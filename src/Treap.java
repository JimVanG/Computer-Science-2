import java.util.HashSet;
import java.util.Random;

/*  
 * James Van Gaasbeck
 PID: J2686979 
 COP 3503 - Computer Science II 
 Professor Mr. Dr. Sean Szumlanski
 Assignment #3 - Treap.java
 Due: Sunday, October 13, 11:59pm
 */

// class that will be used as each treap node
// Makes the class extend comparable and have the data type be generic,
// also forces the class to implement the compareTo() interface method.
class Node<DoWhatchaLike extends Comparable<DoWhatchaLike>> implements
		Comparable<Node<DoWhatchaLike>> {
	// class variable for random number generator
	private static Random randy = new Random();
	// class variable to hold all of the priority's to check if there's a
	// duplicate.
	private static HashSet<Integer> prioritySet = new HashSet<>();

	// we need the data of the node to be of the generic type 'DoWhatchaLike'
	DoWhatchaLike data;
	Node<DoWhatchaLike> left, right;
	int priority = 0;

	// constructor that sets the passed in data as the nodes data, and
	// automatically sets the priority of the node.
	// Does not accept duplicate integers
	Node(DoWhatchaLike data) {
		this.data = data;
		priority = randy.nextInt();
		// System.out.println("Priority: " + priority);
		// if the number isn't in the hashSet then use the random number we
		// generated
		if (!prioritySet.contains(priority)) {
			prioritySet.add(priority);
			// System.out.println("Added: " + priority);
		}
		// otherwise we need to keep on generating random numbers until we get a
		// new unique one
		else {
			while (prioritySet.contains(priority)) {
				priority = randy.nextInt();
				// System.out.println("New Priority: " + priority);
			}// pop out of the loop when we get a random number that isn't in
				// the hashSet
			prioritySet.add(priority);
			// System.out.println("Added New Priority: " + priority);
		}
		/*
		 * System.out.println("The HashSet contains:"); for (Integer content :
		 * prioritySet) { System.out.println(content); }
		 */}

	// constructor for if we want to pass in the priority of the node.
	Node(DoWhatchaLike data, int priority) {
		this.data = data;
		this.priority = priority;
		//System.out.println("The user specified Node Constructor was called: "
		//		+ this);
	}

	// the compare to method that is overridden.
	// must take in a generic node
	@Override
	public int compareTo(Node<DoWhatchaLike> that) {
		// if both the data is null return a 0, because there is nothing there.
		if (this.data == null && that.data == null)
			return 0;
		// if this.data is null, then we say that, it is lessThan (-1) that.data
		if (this.data == null)
			return -1;
		// if that.data is null, then we say that, this.data is greaterThan (1)
		// that.data
		if (that.data == null)
			return 1;
		// System.out.println("compareTo(): " + data.compareTo(that.data));
		// otherwise, do a regular ol' compareTo method on the data.
		return this.data.compareTo(that.data);
	}

	@Override
	public String toString() {
		return "(Data: " + data + " Priority: " + priority + ")";
	}
}

public class Treap<DoWhatchaLike extends Comparable<DoWhatchaLike>> {

	// root node
	private Node<DoWhatchaLike> root = null;
	private int numberOfNodes;

	// method to encapsulate adding to the treap
	public void add(DoWhatchaLike data) {
		root = add(root, data);
	}

	private Node<DoWhatchaLike> add(Node<DoWhatchaLike> root, DoWhatchaLike data) {

		if (isEmpty(root)) {
			numberOfNodes++;
			return new Node<DoWhatchaLike>(data);
		} else if (data.compareTo(root.data) < 0) {
			root.left = add(root.left, data);
			if (root.left.priority < root.priority) {
				root = rotateRight(root);
			}
		} else if (data.compareTo(root.data) > 0) {
			root.right = add(root.right, data);
			if (root.right.priority < root.priority) {
				root = rotateLeft(root);
			}
		}
		return root;
	}

	public void add(DoWhatchaLike data, int priority) {
		root = add(root, data, priority);
	}

	// ADD to the treap but the priority will be specified in the node
	// constructor by the programmer (Sean)
	// keep track of the number of nodes when adding
	private Node<DoWhatchaLike> add(Node<DoWhatchaLike> addNode,
			DoWhatchaLike data, Integer priority) {

		// check if the tree is empty, if it is make the node we are inserting
		// the root
		if (isEmpty(addNode)) {
			numberOfNodes++;
			return new Node<DoWhatchaLike>(data, priority);
			// do the BST insertion logic
		} else if (data.compareTo(addNode.data) < 0) {
			addNode.left = add(addNode.left, data, priority);
			// do the Treap/Heap logic
			// if the new childs priority is less than the parents we need to do
			// a rotate right
			if (addNode.left.priority < addNode.priority) {
				addNode = rotateRight(addNode);
			}
		} else if (data.compareTo(addNode.data) > 0) {
			addNode.right = add(addNode.right, data, priority);
			if (addNode.right.priority < addNode.priority) {
				addNode = rotateLeft(addNode);
			}
		}

		return addNode;
	}

	// method to rotate right
	// essentially a swap-di-swoop
	private Node<DoWhatchaLike> rotateRight(Node<DoWhatchaLike> exParent) {
		// make a node and fill it with the contents of the current parent
		// node's left child
		Node<DoWhatchaLike> nodeToBeParent = exParent.left;
		// whatever is/was to the right of the new parent node is now to the
		// left of the the old parent
		// this is just how treap rotations work.
		exParent.left = nodeToBeParent.right;
		// the old parent is now the right child of the new parent.
		nodeToBeParent.right = exParent;

		return nodeToBeParent;
	}

	// method to rotate left
	private Node<DoWhatchaLike> rotateLeft(Node<DoWhatchaLike> exParent) {
		Node<DoWhatchaLike> nodeToBeParent = exParent.right;
		exParent.right = nodeToBeParent.left;
		nodeToBeParent.left = exParent;
		return nodeToBeParent;
	}

	// method to encapsulate the remove method
	// keep track of the number of nodes when deleting
	public void remove(DoWhatchaLike data) {
		if (contains(data)) {
			numberOfNodes--;
			root = remove(root, data);
		}
	}

	// remove the specified value from the treap
	private Node<DoWhatchaLike> remove(Node<DoWhatchaLike> root,
			DoWhatchaLike data) {

		if (isEmpty(root)) {
			return null;
		} else if (data.compareTo(root.data) < 0) {
			root.left = remove(root.left, data);
		} else if (data.compareTo(root.data) > 0) {
			root.right = remove(root.right, data);
		} else { // we have a hit, so handle the logic of a deletion.

			// delete the node easy if it didn't have any children.
			if (root.left == null && root.right == null) {
				//System.out.println("Deleting a leaf node: " + root);
				return null;
			} else if (root.right == null) {
				//System.out.println("Deleting a node with no right: " + root);
				return root.left;
			} else if (root.left == null) {
				//System.out.println("Deleting a node with no left: " + root);
				return root.right;
			} else {
				//System.out.println("Deleted a node with two kids :[");
				if (root.left.priority < root.right.priority) {
					//if the left priority is less than the right, rotate right to maintain heapness
					root = rotateRight(root);
				} else {
					root = rotateLeft(root);
				}
				if (root != null) {
					root = remove(root, data);
				} 
			}
		}
		return root;
	}


	// method to encapsulate the contains method
	public boolean contains(DoWhatchaLike data) {
		return contains(root, data);
	}

	// method to see if the treap contains the specified data value.
	//pretty much just a BST search
	private boolean contains(Node<DoWhatchaLike> root, DoWhatchaLike data) {

		if (isEmpty(root)) {
			return false;
		} else if (data.compareTo(root.data) < 0) {
			return contains(root.left, data);
		} else if (data.compareTo(root.data) > 0) {
			return contains(root.right, data);
		} else {
			return true;
		}
	}

	// return the number of nodes in the tree
	// keep track of nodes when deleting/adding
	// to ensure O(1) time.
	public int size() {
		return numberOfNodes;
	}

	// public method to call private height.
	public int height() {
		return height(root);
	}

	// traverses the tree to find the height of it.
	private int height(Node<DoWhatchaLike> root) {

		// if the tree is empty then return negative one
		if (isEmpty(root)) {
			return -1;
		}
		// traverse the left
		int leftH = height(root.left);
		int rightH = height(root.right); // traverse the right

		// return the greater one, plus one.
		return (leftH > rightH) ? leftH + 1 : rightH + 1;

	}

	// checks to see if the treap is empty
	private boolean isEmpty(Node<DoWhatchaLike> root) {
		boolean retVal = false;
		if (root == null) {
			retVal = true;
		}
		return retVal;
	}

	// Getter for the root Node.
	public Node<DoWhatchaLike> getRoot() {
		return root;
	}

	public static double difficultyRating() {
		return 2.6;

	}

	public static double hoursSpent() {
		return 7.5;

	}

	// MAIN
	public static void main(String[] args) {
		/*
		 * Treap<Integer> t = new Treap<>(); t.add(3); t.add(4); t.add(13);
		 * System.out.println(t.contains(3));
		 * System.out.println(t.contains(66)); Traversals.inorder(t);
		 * System.out.println("The size of the 't' tree: " + t.size());
		 * System.out.println("The height of the 't' tree: " + t.height());
		 * 
		 * Treap<Integer> t2 = new Treap<Integer>(); t2.add(3, 2); t2.add(4,
		 * 42); t2.add(13, 1); t2.add(1, 432); t2.add(22, 5); t2.add(32, 13);
		 * Traversals.inorder(t2); System.out.println(t2.contains(3)); //
		 * t2.remove(3); System.out.println(t2.contains(3));
		 * System.out.println(t2.contains(66)); t2.remove(66); // t2.remove(1);
		 * t2.remove(4); Traversals.inorder(t2);
		 * System.out.println("The size of the 't2' tree: " + t2.size());
		 * System.out.println("The height of the 't2' tree: " + t2.height());
		 * 
		 * Treap<Integer> justOne = new Treap<Integer>(); justOne.add(1);
		 * System.out.println(justOne.contains(3));
		 * System.out.println(justOne.contains(66));
		 * Traversals.inorder(justOne); justOne.remove(1);
		 * Traversals.inorder(justOne);
		 * System.out.println("The size of the 'justOne' tree: " +
		 * justOne.size());
		 * System.out.println("The height of the 'justOne' tree: " +
		 * justOne.height());
		 * 
		 * Treap<Integer> empty = new Treap<Integer>();
		 * Traversals.inorder(empty); empty.remove(8);
		 * Traversals.inorder(empty);
		 * System.out.println("The size of the 'empty' tree: " + empty.size());
		 * System.out.println("The height of the 'empty' tree: " +
		 * empty.height());
		 */

		
		Treap<Integer> t = new Treap<Integer>();

		// insert values into treap
		t.add(8, 12);
		t.add(2, 190);
		t.add(3, 18);
		t.add(40, 16);
		t.add(5, 47);
		t.add(6, 44);
		t.add(20, 10);
		t.add(40, 1); // notice attempt to insert duplicate value

		// print tree traversals
		Traversals.inorder(t);
		Traversals.preorder(t);
		Traversals.postorder(t);
		System.out.println();

		// print treap stats
		System.out.println("Treap size: " + t.size());
		System.out.println("Treap height: " + t.height());
		System.out.println();

		// attempt to delete values not in treap
		t.remove(42);
		t.remove(43);

		// delete value 3 from treap
		t.remove(3);

		// print tree traversals
		Traversals.inorder(t);
		Traversals.preorder(t);
		Traversals.postorder(t);
		System.out.println();

		// print treap stats
		System.out.println("Treap size: " + t.size());
		System.out.println("Treap height: " + t.height());
		 

		
		Treap<String> test = new Treap<String>();
		test.add("wl",162283549);
		test.add("nx",911094035);
		test.add("dj",742034874);
		test.add("it",1501123419);
		test.add("rz",938870320);
		test.add("je",1118164710);
		test.add("jq",167394538);
		test.add("mx",684413799);
		test.add("kn",1496528339);
		test.add("cl",376379770);
		test.add("kd",359614604);
		test.add("sx",1524460400);
		test.add("tq",760316997);
		test.add("xg",1752446483);
		test.add("ze",1951832343);
		test.add("md",428940290);
		test.add("bu",274689379);
		test.add("ly",1861013263);
		test.add("em",447456902);
		test.add("iw",1206634637);
		test.add("km",83593139);
		test.add("ub",282106385);
		test.add("kn",1221832962);
		test.add("gc",1525033606);
		test.add("ue",1656119623);
		test.add("ur",214456013);
		test.add("iy",968163872);
		test.add("zs",268929981);
		test.add("yc",924968204);
		test.add("fv",1721765354);
		test.add("mx",1116026128);
		test.add("rk",1832896787);
		test.add("ny",1634289337);
		test.add("bu",473798582);
		test.add("ex",1429833630);
		test.add("ik",731061735);
		test.add("zg",1970042911);
		test.add("kg",2134796961);
		test.add("fk",1369715356);
		test.add("ch",1347073532);
		test.add("cc",75113922);
		test.add("fk",411021969);
		test.add("nj",1483328321);
		test.add("cr",1182599327);
		test.add("oh",115555318);
		test.add("zq",787722032);
		test.add("gp",1712958443);
		test.add("do",1155595114);
		test.add("gx",1254290053);
		test.add("xl",1799838376);
		test.add("ce",1540645835);
		test.add("ie",838782248);
		test.add("eg",24535721);
		test.add("tv",503011505);
		test.add("ua",1175138529);
		test.add("tp",183617555);
		test.add("yg",1180607317);
		test.add("dk",1706241327);
		test.add("vv",688095286);
		test.add("ve",252558615);
		test.add("on",1185443241);
		test.add("if",1469954707);
		test.add("uh",1575581268);
		test.add("zg",484132444);
		test.add("qq",183781913);
		test.add("ka",1543113071);
		test.add("sp",442269859);
		test.add("dp",1123930652);
		test.add("af",1222264091);
		test.add("ws",752571974);
		test.add("ln",394325493);
		test.add("uu",162952458);
		test.add("qq",325331876);
		test.add("sq",1299147017);
		test.add("zo",1958842073);
		test.add("sv",1742112202);
		test.add("en",1052332145);
		test.add("xo",1680487252);
		test.add("bo",1703698171);
		test.add("vq",806682348);
		test.add("vx",1414977597);
		test.add("yw",576305050);
		test.add("ny",2020947261);
		test.add("rn",165259866);
		test.add("af",172985966);
		test.add("ad",1338324837);
		test.add("oa",1363770694);
		test.add("jp",984683308);
		test.add("yi",1361986217);
		test.add("wt",689613505);
		test.add("rb",113244039);
		test.add("oj",599540104);
		test.add("fl",959818959);
		test.add("em",67993193);
		test.add("ta",2005893699);
		test.add("pn",1619999936);
		test.add("rd",685300156);
		test.add("gi",472488588);
		test.add("tc",1507322501);
		test.add("cx",1796298182);
		test.add("fo",593961056);
		test.add("fy",125678114);
		test.add("pj",48743582);
		test.add("qa",168966909);
		test.add("os",805740145);
		test.add("iu",442489069);
		test.add("qb",1821002852);
		test.add("vh",510054734);
		test.add("mz",1312308533);
		test.add("rl",1974428382);
		test.add("jo",1344819010);
		test.add("tl",478206196);
		test.add("rd",1312832947);
		test.add("dd",454494736);
		test.add("lw",1327963148);
		test.add("yt",647255650);
		test.add("br",1492012582);
		test.add("jt",787635852);
		test.add("ol",185629540);
		test.add("zx",1300088431);
		test.add("dn",1935681236);
		test.add("fc",127499320);
		test.add("el",1194242467);
		test.add("vw",2092701643);
		test.add("eh",1484369389);
		test.add("lg",1307969969);
		test.add("qz",812091032);
		test.add("ir",2146528989);
		test.add("uf",451781866);
		test.add("og",1130659723);
		test.add("ph",927028922);
		test.add("ei",1719513073);
		test.add("vf",668065126);
		test.add("jd",1023719082);
		test.add("bk",52243373);
		test.add("jf",1273352723);
		test.add("ve",1926886855);
		test.add("du",213696246);
		test.add("dq",1825627514);
		test.add("mn",970652066);
		test.add("dw",1383308671);
		test.add("di",1683029505);
		test.add("ft",569129663);
		test.add("ov",1926519729);
		test.add("eo",1528783457);
		test.add("zx",1058313920);
		test.add("kn",286538155);
		test.add("tk",2051991540);
		test.add("dn",1585513825);
		test.add("sm",181737103);
		test.add("oz",184496321);
		test.add("kt",568704015);
		test.add("xy",331503378);
		test.add("dw",549744036);
		test.add("cc",448600062);
		test.add("at",1083596281);
		test.add("nn",1543740486);
		test.add("lj",591670502);
		test.add("km",2065092715);
		test.add("fm",61062905);
		test.add("od",246709021);
		test.add("it",93654230);
		test.add("kf",1039103552);
		test.add("lx",2125137561);
		test.add("ys",1053847478);
		test.add("yc",1586347062);
		test.add("kg",900905144);
		test.add("ow",1117594793);
		test.add("hh",2065287375);
		test.add("xj",798750486);
		test.add("ra",2012953420);
		test.add("om",1366306111);
		test.add("uw",680406215);
		test.add("aa",2104681839);
		test.add("fy",1673264711);
		test.add("kl",1048279462);
		test.add("yk",1064281555);
		test.add("vv",2011808155);
		test.add("cb",1951911805);
		test.add("qp",831624542);
		test.add("hs",1750939526);
		test.add("sn",2058209213);
		test.add("zq",1446101735);
		test.add("bw",1426779754);
		test.add("ob",940964547);
		test.add("xy",1753902003);
		test.add("mg",677491671);
		test.add("ir",1964266066);
		test.add("ma",362922837);
		test.add("yx",1193990261);
		test.add("la",441961789);
		test.add("na",1085550463);
		test.add("qt",800738235);
		test.add("us",290961065);
		test.add("zk",593107586);
		test.add("ey",857317386);
		test.add("ap",1265625715);
		test.add("up",1909734155);
		test.add("im",1827086611);
		test.add("sb",770194788);
		test.add("tz",656723726);
		test.add("yo",18709988);
		test.add("jq",807960419);
		test.add("pu",995342910);
		test.add("kf",706332279);
		test.add("si",879696174);
		test.add("fq",968923038);
		test.add("ex",1224623425);
		test.add("ry",568821583);
		test.add("fo",1821380422);
		test.add("ip",106839753);
		test.add("eh",1923921078);
		test.add("zx",1683982284);
		test.add("ip",2131425896);
		test.add("qm",1973516023);
		test.add("vm",637761523);
		test.add("lx",1299143031);
		test.add("fq",747930251);
		test.add("pe",1744580197);
		test.add("qb",111104892);
		test.add("bc",1036095101);
		test.add("it",998448298);
		test.add("mx",1062865468);
		test.add("kz",66904732);
		test.add("ve",2012685811);
		test.add("za",1566249342);
		test.add("om",695874882);
		test.add("zd",146924252);
		test.add("hj",2138341634);
		test.add("ov",579980239);
		test.add("lg",1443248663);
		test.add("wt",219891308);
		test.add("al",1826414587);
		test.add("ez",130772976);
		test.add("tx",1100014363);
		test.add("gz",604180231);
		test.add("cd",158819795);
		test.add("pp",469088750);
		test.add("qj",298329045);
		test.add("bb",1432416420);
		test.add("mv",1809360426);
		test.add("kq",1589847126);
		test.add("yw",507840790);
		test.add("cr",1025031286);
		test.add("yk",1162590650);
		test.add("lr",1126782760);
		test.add("aj",746893886);
		test.add("ca",2033816345);
		test.add("ya",278256242);
		test.add("zu",967171767);
		test.add("wl",481644915);
		test.add("fn",1872814014);
		test.add("ry",1607436517);
		test.add("yd",1244408312);
		test.add("vk",1210201372);
		test.add("id",1568938725);
		test.add("pg",539900195);
		test.add("ep",364892549);
		test.add("eu",556390279);
		test.add("fi",2037667140);
		test.add("si",1337558814);
		test.add("uy",333325952);
		test.add("qm",1070135565);
		test.add("ce",848202286);
		test.add("hx",1376187047);
		test.add("gw",133826887);
		test.add("rk",1826996152);
		test.add("lh",1168882217);
		test.add("wi",1312237126);
		test.add("sm",2137851859);
		test.add("qm",1906696305);
		test.add("jv",2072235654);
		test.add("gf",1234158055);
		test.add("ha",677430463);
		test.add("fd",1866125233);
		test.add("cw",1758872373);
		test.add("ff",120369262);
		test.add("gr",2119411241);
		test.add("qf",1320090197);
		test.add("fs",2076940802);
		test.add("gf",375653604);
		test.add("xd",553372797);
		test.add("ni",1868498454);
		test.add("zu",2145555826);
		test.add("gn",1721196124);
		test.add("wo",778784918);
		test.add("vu",501881555);
		test.add("ff",198982341);
		test.add("lb",810407170);
		test.add("ax",695782231);
		test.add("bq",209990359);
		test.add("te",54107627);
		test.add("fe",143749010);
		test.add("bf",1368335351);
		test.add("qa",11891588);
		test.add("wa",1996387075);
		test.add("wx",78323420);
		test.add("bo",1495770772);
		test.add("dn",833719984);
		test.add("sk",1066101264);
		test.add("az",1903706968);
		test.add("iv",475095873);
		test.add("pu",1704078047);
		test.add("pa",595808527);
		test.add("kr",1253357999);
		test.add("kj",1489556765);
		test.add("pk",987714555);
		test.add("mw",1171128115);
		test.add("hr",1404333993);
		test.add("jt",1967312867);
		test.add("pp",921760883);
		test.add("zo",1726744412);
		test.add("zx",1948745830);
		test.add("bz",497924148);
		test.add("do",685004252);
		test.add("xo",1139684079);
		test.add("sk",1218697689);
		test.add("sz",1582039324);
		test.add("mb",864069943);
		test.add("jx",126076050);
		test.add("ym",802121888);
		test.add("to",2007692101);
		test.add("ng",1631950966);
		test.add("zi",1471316729);
		test.add("uf",36032031);
		test.add("lc",1637399600);
		test.add("sl",2105466579);
		test.add("wf",216763232);
		test.add("ah",326409104);
		test.add("pl",1532806131);
		test.add("uk",414706027);
		test.add("np",223341778);
		test.add("jp",440997931);
		test.add("fe",2073382088);
		test.add("cw",243957863);
		test.add("qm",555223686);
		test.add("uv",581602561);
		test.add("cz",1916623286);
		test.add("jt",597915481);
		test.add("kt",1529923266);
		test.add("sw",1184635502);
		test.add("oh",461233333);
		test.add("ld",1568171543);
		test.add("pp",1665234253);
		test.add("hk",134288479);
		test.add("uw",437071433);
		test.add("qi",207186390);
		test.add("lu",208508608);
		test.add("fc",1419699928);
		test.add("uc",1946143661);
		test.add("kj",598338865);
		test.add("ed",776297097);
		test.add("ol",1981252506);
		test.add("ik",487415232);
		test.add("pp",1798011349);
		test.add("ur",729279145);
		test.add("hj",1960269314);
		test.add("iu",1970079646);
		test.add("no",1332056122);
		test.add("vm",1945795975);
		test.add("cb",1175568481);
		test.add("ts",210501061);
		test.add("et",508922144);
		test.add("ee",511127829);
		test.add("yt",2047287356);
		test.add("rd",1795253414);
		test.add("xi",1353809514);
		test.add("hp",1861660061);
		test.add("yq",149904196);
		test.add("hz",1195538163);
		test.add("uw",522795797);
		test.add("ad",1188532293);
		test.add("qu",1375990904);
		test.add("lx",1421473001);
		test.add("ms",238040199);
		test.add("ti",205954646);
		test.add("cu",1631889385);
		test.add("sy",1426447141);
		test.add("gj",1375439793);
		test.add("ri",361414380);
		test.add("ek",498176200);
		test.add("oa",926834883);
		test.add("cl",1870599306);
		test.add("mo",244825916);
		test.add("hd",450221321);
		test.add("ol",1632190186);
		test.add("qb",1314154586);
		test.add("hw",628808588);
		test.add("jc",1839061713);
		test.add("fv",995901090);
		test.add("st",177667348);
		test.add("wv",206836727);
		test.add("xc",362022066);
		test.add("tq",1039274156);
		test.add("al",1021331305);
		test.add("uc",229590281);
		test.add("ah",1220474618);
		test.add("ed",378825513);
		test.add("gl",565109331);
		test.add("it",170921083);
		test.add("qv",964453314);
		test.add("gg",2013163818);
		test.add("nh",144949377);
		test.add("ft",1017344583);
		test.add("ag",1121149126);
		test.add("tw",1446666470);
		test.add("xl",1059087691);
		test.add("wn",329931946);
		test.add("pt",1222197937);
		test.add("gd",662539018);
		test.add("eo",1089545395);
		test.add("il",1313851962);
		test.add("hi",132383509);
		test.add("hi",1185255657);
		test.add("hj",1557032046);
		test.add("sk",1392366804);
		test.add("aj",107293831);
		test.add("pn",1146743641);
		test.add("nk",698357086);
		test.add("hi",1852079130);
		test.add("gv",1651003308);
		test.add("be",1341438412);
		test.add("aa",613268196);
		test.add("al",1241856225);
		test.add("mf",1154482131);
		test.add("pf",1769330936);
		test.add("pb",386342251);
		test.add("lv",2016827169);
		test.add("fm",1636587406);
		test.add("fp",1105412344);
		test.add("ob",1997506973);
		test.add("co",1838225763);
		test.add("hj",354813448);
		test.add("lj",261729520);
		test.add("dd",1396132163);
		test.add("fp",635282151);
		test.add("ro",1696129770);
		test.add("nn",674269507);
		test.add("of",114171328);
		test.add("xa",78621388);
		test.add("bv",1771637735);
		test.add("cv",1773415072);
		test.add("co",1777974819);
		test.add("cz",1074198215);
		test.add("tv",1954384107);
		test.add("va",1629047897);
		test.add("po",1954243111);
		test.add("ym",1298570710);
		test.add("im",573274445);
		test.add("kv",736941912);
		test.remove("mc");
		test.remove("si");
		test.remove("at");
		test.remove("oj");
		test.remove("uq");
		test.remove("gq");
		test.remove("ju");
		test.remove("yu");
		test.remove("tb");
		test.remove("wm");
		test.remove("fs");
		test.remove("rh");
		test.remove("rq");
		test.remove("nx");
		test.remove("jx");
		test.remove("ax");
		test.remove("zc");
		test.remove("xg");
		test.remove("bs");
		test.remove("rg");
		test.remove("rj");
		test.remove("ik");
		test.remove("tc");
		test.remove("dn");
		test.remove("bw");
		test.remove("sy");
		test.remove("vt");
		test.remove("ab");
		test.remove("ou");
		test.remove("ko");
		test.remove("vj");
		test.remove("ka");
		test.remove("ei");
		test.remove("tu");
		test.remove("ff");
		test.remove("ry");
		test.remove("bv");
		test.remove("ak");
		test.remove("qe");
		test.remove("vi");
		test.remove("cx");
		test.remove("xq");
		test.remove("rz");
		test.remove("gk");
		test.remove("nh");
		test.remove("vi");
		test.remove("ic");
		test.remove("vj");
		test.remove("cx");
		test.remove("ry");
		test.remove("cr");
		test.remove("ms");
		test.remove("zk");
		test.remove("ob");
		test.remove("qn");
		test.remove("yh");
		test.remove("uz");
		test.remove("ev");
		test.remove("jb");
		test.remove("tn");
		test.remove("mu");
		test.remove("jg");
		test.remove("xj");
		test.remove("fa");
		test.remove("eo");
		test.remove("hr");
		test.remove("xz");
		test.remove("yz");
		test.remove("jj");
		test.remove("an");
		test.remove("ir");
		test.remove("uo");
		test.remove("ns");
		test.remove("td");
		test.remove("og");
		test.remove("kx");
		test.remove("hi");
		test.remove("wu");
		test.remove("tn");
		test.remove("zv");
		test.remove("da");
		test.remove("kl");
		test.remove("mv");
		test.remove("ws");
		test.remove("ts");
		test.remove("sf");
		test.remove("hn");
		test.remove("tw");
		test.remove("lm");
		test.remove("lw");
		test.remove("cr");
		test.remove("vz");
		test.remove("qe");
		test.remove("gm");
		test.remove("eq");
		test.remove("nc");
		test.remove("lp");
		test.remove("nz");
		test.remove("ee");
		test.remove("ze");
		Traversals.preorder(test);
		System.out.println(test.size());
		System.out.println(test.height());
	}

}
