package com.cv4j.core.filters;

import com.cv4j.core.datamodel.ImageData;
import com.cv4j.image.util.Tools;

public class EmbossFilter implements CommonFilter {
	private int COLORCONSTANTS;
	private boolean out;

	public EmbossFilter() {
		this.COLORCONSTANTS = 100;
	}

	public EmbossFilter(boolean out) {
		this.out = out;
		this.COLORCONSTANTS = 100;
	}

	@Override
	public ImageData filter(ImageData src){
		int width = src.getWidth();
        int height = src.getHeight();

		int offset = 0;
		int r1=0, g1=0, b1=0;
		int r2=0, g2=0, b2=0;
		int r=0, g=0, b=0;
		byte[] R = src.getChannel(0);
		byte[] G = src.getChannel(1);
		byte[] B = src.getChannel(2);
		byte[][] output = new byte[3][R.length];
		for ( int y = 1; y < height-1; y++ ) {
			offset = y*width;
			for ( int x = 1; x < width-1; x++ ) {
				r1 = R[offset] & 0xff;
				g1 = G[offset] & 0xff;
				b1 = B[offset] & 0xff;

				r2 = R[offset+width] & 0xff;
				g2 = G[offset+width] & 0xff;
				b2 = B[offset+width] & 0xff;

				if(out) {
					r = r1 - r2;
					g = g1 - g2;
					b = b1 - b2;
				} else {
					r = r2 - r1;
					g = g2 - g1;
					b = b2 - b1;
				}
				r = Tools.clamp(r+COLORCONSTANTS);
				g = Tools.clamp(g+COLORCONSTANTS);
				b = Tools.clamp(b+COLORCONSTANTS);

				output[0][offset] = (byte)r;
				output[1][offset] = (byte)g;
				output[2][offset] = (byte)b;
				offset++;
			}
		}
		src.putPixels(output);
		output = null;
		return src;
	}

	/**
	 * 
	 * @param out
	 */
	public void setOUT(boolean out) {
		this.out = out;
	}
}
