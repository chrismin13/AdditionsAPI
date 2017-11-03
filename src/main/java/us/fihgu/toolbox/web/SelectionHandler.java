package us.fihgu.toolbox.web;

import java.nio.channels.SelectionKey;

public interface SelectionHandler
{
	void handleSelection(SelectionKey selectionKey);
	int getDefaultInterestSet();
	void onRegister(SelectionKey key);
}
