package codingInterview;

public class RectangleTest {
	
	static class Rectangle{
		int x,y,width, height;
		
		public Rectangle(int x, int y, int width, int height){
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		@Override
		public String toString() {
			return "Rectangle: x=" + this.x + ", y="
					+ this.y + ", Width=" + this.width + ", Height=" + this.height;
		}
	}
	
	static boolean isIntersect(Rectangle r, Rectangle s){
		
		return (r.x <= s.x + s.width) && (r.x + r.width >= s.x) &&
				(r.y <= s.y + s.height) && (r.y + r.height >= s.y);
		
	}
		

	static Rectangle getIntersection(Rectangle r, Rectangle s){
		
		if(isIntersect(r, s)){
			return new Rectangle(
					Math.max(r.x, s.x), Math.max(r.y, s.y),
					(Math.min(r.x + r.width, s.x + s.width) - Math.max(r.x, s.x)),
					(Math.min(r.y + r.height, s.y + s.height) - Math.max(r.y, s.y)));
		}else
			return new Rectangle(0, 0, -1, -1);
		
	}
	
	public static void main(String[] args) {
		
		Rectangle r = new Rectangle(2, 2, 10, 10);
		Rectangle s = new Rectangle(5, 5, 10, 10);
		
		System.out.println(getIntersection(r, s).toString());
		
	}

}
