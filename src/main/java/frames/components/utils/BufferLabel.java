package frames.components.utils;

import javax.swing.JLabel;

public class BufferLabel extends JLabel {

	private static final long serialVersionUID = 7099488327950948450L;

	public BufferLabel(int size) {
		super();
		String bufferString = getBufferString(size);
		this.setText(bufferString);
	}

	private String getBufferString(int size) {
		String bufferString = "";
		for (int i=0; i < size; i++) {
			bufferString = bufferString + " ";
		}
		return bufferString;
	}

}
