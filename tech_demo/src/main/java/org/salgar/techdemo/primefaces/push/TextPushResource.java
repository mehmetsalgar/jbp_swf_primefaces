package org.salgar.techdemo.primefaces.push;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.annotation.Singleton;
import org.primefaces.push.impl.JSONEncoder;

import javax.faces.application.FacesMessage;

@PushEndpoint("/text_list")
@Singleton
public class TextPushResource {

	@OnMessage(encoders = { JSONEncoder.class})
	public FacesMessage onMessage(FacesMessage message) {
		return message;
	}
}
