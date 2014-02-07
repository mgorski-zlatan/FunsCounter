package com.foursee.funscounter.ui.dialog;

import android.app.ProgressDialog;
import android.content.Context;

public class LoadingDialog {

	private final ProgressDialog dialog;

	public LoadingDialog(Context context, int messageId) {
		dialog = new ProgressDialog(context);
		dialog.setCancelable(false);
		dialog.setIndeterminate(false);
		dialog.setMessage(context.getString(messageId));
	}

	public void show() {
		dialog.show();
	}

	public void dismiss() {
		dialog.dismiss();
	}
}