package dummy;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		int screenHeight = 1366;
		int screenWidth = 768;
		
		int imgHeight=1366;
		int imgWidth=960;
		
		System.out.println();
		System.out.println(screenHeight + "   " + screenWidth);
		// g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		System.out.println(imgHeight + "   " + " imgHe" + imgWidth);
		float p = screenHeight / imgHeight;
		System.out.println(p);
		float q = (float)screenWidth / imgWidth;
		System.out.println(q);
		System.out.println();
		float pFactor = 1;
		float qFactor = 1;
		if (imgWidth > screenWidth) {
			pFactor = imgHeight / screenHeight;
			System.out.println("pFactor" + pFactor);

		} else if (imgHeight >= screenHeight) {
			qFactor = imgHeight / screenHeight;
			System.out.println("qFactor" + qFactor);
		}

		screenHeight = (int) (screenHeight * qFactor);
		screenWidth = (int) (screenWidth * pFactor);
	
		float m = Math.min(p, q);
		System.out.println(m);
		int scaleX = (int) (imgWidth * m);
		System.out.println(scaleX);

		int scaleY = (int) (imgHeight * m);
		System.out.println(scaleY);
	}

}
