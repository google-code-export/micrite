/* ===========================================================
 * $Id$
 * This file is part of Micrite
 * ===========================================================
 *
 * (C) Copyright 2009, by Gaixie.org and Contributors.
 * 
 * Project Info:  http://micrite.gaixie.org/
 *
 * Micrite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Micrite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Micrite.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gaixie.micrite.struts2.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * This class provide a wrapper for an http response
 * 
 * @author hunk
 */
public class ResponseWrapper {
	private String contentType = "application/json";
	private String characterEncoding = "utf-8";
	private boolean gzip;
	private boolean noCache;
	private int statusCode = 200;

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

	public void setGzip(boolean gzip) {
		this.gzip = gzip;
	}

	public void setNoCache(boolean noCache) {
		this.noCache = noCache;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public void writeResult(HttpServletRequest request,
			HttpServletResponse response, String result) throws IOException {
		response.setContentType(contentType);
		response.setStatus(statusCode);
		response.setCharacterEncoding(characterEncoding);

		if (noCache) {
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "No-cache");
		}

		if (gzip && isGzipInRequest(request)) {
			response.addHeader("Content-Encoding", "gzip");
			GZIPOutputStream out = null;
			try {
				out = new GZIPOutputStream(response.getOutputStream());
				out.write(result.getBytes(characterEncoding));
			} finally {
				if (out != null) {
					out.finish();
					out.close();
				}
			}
		} else {
			int contentLength = result.getBytes(characterEncoding).length;
			response.setContentLength(contentLength);
			PrintWriter out = response.getWriter();
			out.write(result);
		}
	}

	/**
	 * check whether gzip encoding is accepted by the browser again borrowed
	 * from struts json plugin
	 * 
	 * @param request
	 * @return
	 */
	private boolean isGzipInRequest(HttpServletRequest request) {
		String header = request.getHeader("Accept-Encoding");
		return header != null && header.indexOf("gzip") >= 0;
	}
}
