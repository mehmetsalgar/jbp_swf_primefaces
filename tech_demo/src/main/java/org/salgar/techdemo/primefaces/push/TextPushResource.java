package org.salgar.techdemo.primefaces.push;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.annotation.Singleton;

@PushEndpoint("/text_list")
@Singleton
public class TextPushResource {

	@OnMessage
	public String onMessage(String message) {
		return message;
	}
}
