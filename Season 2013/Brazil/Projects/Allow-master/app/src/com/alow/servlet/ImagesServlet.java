/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alow.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alow.model.UploadUrl;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.utils.SystemProperty;

/**
 * Provides an API for creating and retrieving URLs to which photo images can be
 * uploaded.
 * 
 * This servlet provides the /api/images end-point, and exposes the following
 * operations:
 * 
 * POST /api/images
 * 
 * @author vicfryzel@google.com (Vic Fryzel)
 */
public class ImagesServlet extends JsonRestServlet {
	/**
	 * BlobstoreService to use for upload URL generation.
	 */
	private final BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	/**
	 * Exposed as `POST /api/images`.
	 * 
	 * Creates and returns a URL that can be used to upload an image for a
	 * photo. Returned URL, after receiving an upload, will fire a callback
	 * (resend the entire HTTP request) to /api/photos.
	 * 
	 * Takes no request payload.
	 * 
	 * Returns the following JSON response representing an upload URL:
	 * 
	 * {
	 *   "url": "http://appid.appspot.com/_ah/upload/upload-key"
	 * }
	 * 
	 * Issues the following errors along with corresponding HTTP response codes:
	 * 401: "Unauthorized request"
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			checkAuthorization(req);
			String uploadUrlString = blobstoreService.createUploadUrl("/api/photos");

			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development) {
				String hostname = System.getProperty("development.hostname");

				if (hostname != null) {
					// modify the url to allow use with the development server
					uploadUrlString = uploadUrlString.replace("localhost", hostname);
				}
			}
			
			UploadUrl uploadUrl = new UploadUrl(uploadUrlString);

			sendResponse(req, resp, uploadUrl);
		} catch (UserNotAuthorizedException e) {
			sendError(resp, 401, "Unauthorized request");
		}
	}
}
