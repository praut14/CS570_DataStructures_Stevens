package hw5_P_Raut;

import java.util.*;
import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable<E>>
{

 private static class Node<E>
 {
	 public E data;
	 public int priority;
	 public Node <E> left;
	 public Node <E> right;
	 
	 //constructor
	 public Node(E data, int priority)
	 {
		 if(data==null)
		 {
			throw new IllegalArgumentException(); 
		 }
		 else
		 {
			 this.data=data;
			 this.priority=priority;
			 this.left=null;
			 this.right=null;
		 }
	 }
	   public Node<E> rotateRight() // right rotation
	   {
		   Node<E> a= this.left;
		   Node<E> left=a.right;
		    a.right=this;
		   this.left=left;
		   return a;
	   }
	   public Node<E> rotateLeft() //left rotation
	   {
		   Node <E> a=this.right;
		   Node<E> right=a.left;
		   a.left=this;
		   this.right=right;
		   return a;
	   }
	   public String toString()
	   {
		   return this.data.toString();
	   }
	 }
         //variables declaration for class Treap
       private Random priorityGenerator;
       private Node<E> root;
     
       public Treap() //default constructor for class Treap
       {
    	 priorityGenerator= new Random();
    	 root=null;
    	   
       }
       public Treap(long seed) //parameterized constructor for initializing  Random priorityGenerator 
       {
    	   priorityGenerator= new Random(seed);
      	 root=null;
 }
       
       public void createheap(Node<E> leafnode, Stack< Node<E>> s)
       {
    	   while(!s.isEmpty())
    	   {
    		   Node<E> parentnode=s.pop();
    		   if(parentnode.priority<leafnode.priority)
    		   {
    			   if(parentnode.data.compareTo(leafnode.data)>0) {
    				   leafnode=parentnode.rotateRight();
    			   }
    			   else
    			   {
    				   leafnode=parentnode.rotateLeft();
    			   }
    			   if(!s.isEmpty())
    			   {
    				   if(s.peek().left==parentnode) {
    					   s.peek().left=leafnode;
    				   }
    				   else
    				   {
    					   s.peek().right=leafnode;
    				   }
    			   }
    			   else
    			   {
    				   this.root=leafnode;
    			   }
    		   }
    		   else
    		   {
    			   break;
    		   }
    	   }
       }
       //method once it has generated the random priority. Thus all the “work” is performed by the latter method
       boolean add(E key)
       {
    	   return add(key,priorityGenerator.nextInt());
       }
       
       /* method to insert the given element into the tree, create a new node containing key as its data and 
a random priority generated by priorityGenerator. The method returns true, if a node with the 
key was successfully added to the treap. If there is already a node containing the given 
key, the method returns false and does not modify the treap.*/
       boolean add(E key,int priority)  
       {
    	   if(root==null)
    	   {
    		   root=new Node<E>(key,priority);
    		   return true;
    	   }
    	   else
    	   {
    		   Node <E> newnode= new Node<E>(key,priority);
    		   Stack <Node<E>> s= new Stack <Node<E>>();
    		   Node<E> newroot=root;
    		   while(newroot!=null)
    		   {
    			   newroot.data.compareTo(key);
    			   if( newroot.data.compareTo(key)==0)
    			   {
    				   return false;
    			   }
    			   else
    			   {
    				   if(newroot.data.compareTo(key)<0) {
    					   s.push(newroot);
    					   if(newroot.right==null) {
    						   newroot.right=newnode;
    						   createheap(newnode,s);
    						   return true;
    					   }
    					   else
    						   newroot=newroot.right;
    				   }
    			   
    			   else
    			   {
    				   s.push(newroot);
    				   if(newroot.left==null) {
    					   newroot.left=newnode;
    					   createheap(newnode,s);
    					   return true;
    				   }
    				   else
    				   {
    					   newroot=newroot.left;
    				   }
    			   }
    		   }
    	   }
    	   return false;
       } 
       }
       /* method to delete the node with the given key from the treap and returns true. If the 
key was not found, the method does not modify the treap and returns false.*/
       public boolean delete(E key)
       {
    	   if(root==null ||find(key)==false)
    	   {
    		   return false;
    	   }
    	   else
    	   {
    		   root=delete(key,root);
    		   return true;
    	   }
    	   
       }
       //method to help delete a node from treap
       private Node<E> delete(E key,Node <E> newroot)
       {
    	   if(newroot==null)
    	   {
    		   return newroot;
    	   }
    	   else
    	   {
    		   if(newroot.data.compareTo(key)<0)
    		   {
    			   newroot.right=delete(key,newroot.right);
    		   }
    		   
    	   
    	   else
    	   {
    		   if(newroot.data.compareTo(key)>0)
    		   {
    			   newroot.left=delete(key,newroot.left);
    		   }
    		   
    		   else
    		   {
    			   if(newroot.right==null)
    			   {
    				   newroot=newroot.left;
    			   }
    			   else
    			   {
    				   if(newroot.left==null)
        			   {
        				   newroot=newroot.right;
        			   }
    				   else
    				   {
    					   if(newroot.right.priority<newroot.left.priority)
    					   {
    						   newroot=newroot.rotateRight();
    						   newroot.right=delete(key,newroot.right);
    					   }
    					   else
    					   {
    						   newroot=newroot.rotateLeft();
    						   newroot.left=delete(key,newroot.left);
    					   }
    				   }
    			   }
    				   
    			   }
    		   }
    	 }
    	   return newroot;
       }
       /* Method finds a node with the given key in the treap rooted at 
root and returns true if it finds it and false otherwise*/
       private boolean find(Node<E> root, E key)
       {
    	   if(root==null)
    	   {
    		   return false;
    	   }
    	   if(key.compareTo(root.data)==0)
    	   {
    		   return true;
    	   }
    	   else if(key.compareTo(root.data)<0)
    	   {
    		   return find(root.left,key);
    		   
    	   }
    	   else 
    	   {
    		   return find(root.right,key);
    	   }
    	   
       }
       /* Method finds a node with the given key in the treap and returns true if it finds it 
and false otherwise.*/
       public boolean find(E key)
       {
    	   if(key==null)
    	   {
    		   throw new NullPointerException("Key cannot be null");
    	   }
    	   return find(root,key);
       }
       public String toString()
       {
    	   StringBuilder str=new StringBuilder();
    	   preOrderTraverse(root,1,str);
    	   return str.toString();
       }
       private void preOrderTraverse(Node <E> node,int depth,StringBuilder str)
       {
    	   for(int i=1;i<depth;i++)
    	   {
    		   str.append(" ");
    	   }
    	   if(node==null)
    	   {
    		   str.append("null");
    	   }
    	   else
    	   {
    		   str.append("(Key="+node.toString()+",");
    		   str.append("priority=");
    		   str.append(node.priority);
    		   str.append(")");
   			   str.append("\n");
    		   preOrderTraverse(node.left,depth+1,str);
    		   preOrderTraverse(node.right,depth+1,str);
    	   }
       }
       public static void main(String[] args)
       {
    	   Treap<Integer> testTree= new Treap<Integer>();
    	   testTree.add(4, 19);
    	   testTree.add(2, 31);
    	   testTree.add(6,70);
    	   testTree.add(1, 84);
    	   testTree.add(3, 12);
    	   testTree.add(5, 83);
    	   testTree.add(7,26);
    	   System.out.println("Deleting a Node: "+ testTree.delete(3)); 
    	   System.out.println("Node Found : "+ testTree.find(6));
    	   System.out.println("Node Found:"+testTree.find(3));
    	   System.out.println(testTree.toString());
    	   
    	   
    	   
       }
}