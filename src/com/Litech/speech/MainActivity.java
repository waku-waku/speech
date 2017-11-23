package com.Litech.speech;


import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends Activity implements TextToSpeech.OnInitListener {

	private TextToSpeech tts;
	private static final String TAG = "TestTTS";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tts = new TextToSpeech(this, this);
	}
	
	@Override
	public void onInit(int status) {
		// TTSèâä˙âª
		if (TextToSpeech.SUCCESS == status) {
			Log.d(TAG, "initialized");
		} else {
			Log.e(TAG, "faile to initialize");
		}
	}
	
	public void speak(View v) {
		EditText editor = (EditText)findViewById(R.id.edit);
		String string = editor.getText().toString();
		
		if (0 < string.length()) {
			if (tts.isSpeaking()) {
				tts.stop();
				return;
			}
		}
		
		tts.setPitch(1.0f);  
		tts.setSpeechRate(1.0f); 
		
		// tts.speak(text, TextToSpeech.QUEUE_FLUSH, null) Ç…
		// KEY_PARAM_UTTERANCE_ID Ç HasMap Ç≈ê›íË
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,"messageID");
		
		//mapÇÃÇ∆Ç±ÇÎÇÕnullÇ≈Ç‡â¬î\
		tts.speak(string, TextToSpeech.QUEUE_FLUSH, map);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		if (tts.isSpeaking())
			tts.stop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (null != tts) {
			// to release the resource of TextToSpeech
			tts.shutdown();
		}
	}
	
}
