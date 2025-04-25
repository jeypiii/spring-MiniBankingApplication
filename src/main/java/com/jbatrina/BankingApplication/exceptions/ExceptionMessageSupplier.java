package com.jbatrina.BankingApplication.exceptions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ExceptionMessageSupplier {
	private static ExceptionMessageSupplier instance = null;

	private static final String errorMessagesFilePath = "src/main/resources/static/";
	private static final String errorMessagesFileName = "errorMessages.json";
	private static Map<String, String> errorMessageMap = new HashMap<String, String>();

	public static ExceptionMessageSupplier getInstance() {
		if (instance == null) {
			ExceptionMessageSupplier.instance = new ExceptionMessageSupplier();
			loadErrorMessages();
			watchFileForErrorMessageChanges();
		}
	
		return ExceptionMessageSupplier.instance;
	}

	public String getErrorMessage(String errorName, String defaultMessage) {
		if (errorMessageMap.containsKey(errorName)) {
			String errorMessage = errorMessageMap.get(errorName);
			if (! errorMessage.isBlank()) {
				return errorMessage;
			}
		}

		return defaultMessage;
	}
	
	private static void loadErrorMessages() {
        InputStream in = null;
		byte[] mapData = null;

		try {
			in = new BufferedInputStream(
					// TODO: make file path configurable
					Files.newInputStream(Paths.get(errorMessagesFilePath + errorMessagesFileName))
				);
			mapData = in.readAllBytes();

			// Convert JSON to map
			ObjectMapper objectMapper = new ObjectMapper();
			ExceptionMessageSupplier.errorMessageMap = objectMapper.readValue(mapData, HashMap.class);
		} catch (IOException e2) {
			e2.printStackTrace();
//			throw new FileNotFoundException("Error message file not found");
		}
	}
	
	private static void watchFileForErrorMessageChanges() {
	  File errorMessagesFileFolder = new File(errorMessagesFilePath);
	  if (errorMessagesFileFolder.exists()) {
		  	Runnable watcher = () -> {
				try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
				  Path path = Paths.get(errorMessagesFileFolder.getAbsolutePath());
				  path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

				  boolean poll = true;
				  System.out.println("Entering poll loop");
				  while (poll) {
					  WatchKey key = watchService.take();
					  if (key.pollEvents().size() > 0) {
						  System.out.println("Error file changed. RELOAD error messages.");
						  loadErrorMessages();
					  }

					  poll = key.reset();
				  }
				} catch (IOException | InterruptedException | ClosedWatchServiceException e) {
				   Thread.currentThread().interrupt();
				}
			};

			Thread thread = new Thread(watcher);
			thread.setDaemon(true);
			thread.start();
	  }
	}
}
