
public class ListElement {

	 public Object node_data;
	 public ListElement next;
	 
	 ListElement(Object data, ListElement next){
		 this.node_data = data;
		 this.next =  next;		 
	 }
	 
	 ListElement(Object object){
		 this(object, null);
	 }
	 
	 Object getObject(){
		 return node_data;
	 }
	 
	 ListElement getNext(){
		 return next;
	 }
}

