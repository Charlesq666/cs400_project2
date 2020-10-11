// --== CS400 File Header Information ==--
// Name: Youlin Qu
// Email: yqu39@wisc.edu
// Team: NC
// TA: Daniel Finer
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A store class that would work any implemented RBT
 * with methods of adding,
 */
public class Store
{

    private RedBlackTree<Product> products; // The RBT that contains all the products

    private static class Product implements Comparable<Product>
    {
        private String name; // name of product
        private double price; // price of the product

        public Product(String name, double price)
        {
            this.name = name;
            this.price = price;
        }

        @Override
        public int compareTo(Product o) {
            return Double.compare(this.price, o.price);
        }

        @Override
        public String toString()
        {
            return "(" + name + ": $" + price + ")";
        }
    }

    public Store()
    {
        products = new RedBlackTree<>();
    }

    /**
     * adding a product to the RBT with its name and price
     * the price of the product should ne unique.
     * @param name of the product
     * @param price of the product
     * @throws IllegalArgumentException when the price is duplicated
     */
    public void add(String name, double price)
    {
        if(lookUp(price) != null)
        {
            System.out.println("Trying to add " + new Product(name, price));
            System.out.println("There exist " + lookUp(price));
            throw new IllegalArgumentException("Can't adding a price already exist!");
        }
        products.insert(new Product(name, price));
    }

    /**
     * look up the product by its price.
     * @param price of the product
     * @return the String representation of the product
     */
    public String lookUp(double price)
    {
        RedBlackTree.Node<Product> pointer = products.root;
        return lookUpHelper(price, pointer);
    }

    /**
     * recursive lookup helper method
     * @param price price of the target
     * @param parent current parent of the traversing process
     * @return a string representation of the product
     */
    private String lookUpHelper(double price, RedBlackTree.Node<Product> parent)
    {
        if(parent == null) return null;

        if(parent.data.price == price) return parent.data.toString();

        String left = lookUpHelper(price, parent.leftChild);
        if(left != null) return left;

        String right = lookUpHelper(price, parent.rightChild);
        if(right != null) return right;

        return null;
    }

    /**
     * lookup the product with highest price
     * @return a string representation of the product
     */
    public String lookMax()
    {
        RedBlackTree.Node<Product> pointer = products.root;
        while(pointer.rightChild != null)
        {
            pointer = pointer.rightChild;
        }
        return pointer.data.toString();
    }

    /**
     * lookup the product with lowest price
     * @return a string representation of the product
     */
    public String lookMin()
    {
        RedBlackTree.Node<Product> pointer = products.root;
        while(pointer.leftChild != null)
        {
            pointer = pointer.leftChild;
        }
        return pointer.data.toString();
    }

    /**
     *
     * print "file " + filename + " added"
     * or "file " + filename " adding failed"
     */
    public void addFile(String fileName)
    {
        try
        {
            Scanner sc = new Scanner(new File(fileName));
            Scanner sc2;
            while(sc.hasNext())
            {
                sc2 = new Scanner(sc.nextLine());
                String name = sc2.next();
                if(name.equals("")) break;
                double price = Double.parseDouble(sc2.next());
                add(name,price);
            }
            System.out.println("file " + fileName + " added");
        }
        catch(FileNotFoundException fnf)
        {
            System.out.println("File doesn't exist");
            System.out.println("file " + fileName + " adding failed");
        }
        catch(NoSuchElementException | NumberFormatException e)
        {
            e.printStackTrace();
            System.out.println("Your file contents are not in the right format");
            System.out.println("file " + fileName + " adding failed");
        }
    }

    @Override
    public String toString()
    {
        return products.toString();
    }


    public int getHeight()
    {
        return getHeightHelper(products.root, 0);
    }

    private int getHeightHelper(RedBlackTree.Node<Product> parent, int height)
    {
        if(parent != null)
        {
            height++;
        }
        else
        {
            return height;
        }
        return Integer.max(getHeightHelper(parent.leftChild, height), getHeightHelper(parent.rightChild,height));
    }
}