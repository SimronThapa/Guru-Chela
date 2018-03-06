package com.kec.guruchela;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;

public class HelloBubblesActivity extends SherlockActivity implements
		OnKeyListener {
	private com.kec.guruchela.DiscussArrayAdapter adapter;
	private ListView lv;
	private EditText editText1;
	ActionMode mMode;
	// from chatbot
	String[][] chatBot = {
			// standard greetings
			{ "hi", "hello", "hey", "hola", "ola", "howdy" },
			{ "hi,how are you", "hey, how are you doing?", "hi there!",
					"hello :)" },

			// question greetings
			{ "how are you?", "how r u", "how r u", "how are you" },
			{ "good", "doing well", "fantastic", "I'm fine, thankyou!" },
			// yes
			{ "yes" },
			{ "no", "NO", "NO!!!!" },
			{ "great", "good", "nice", "cool" },
			{ "thanks", "thank you" },
			{ "so...", "so" },
			{ "so what?", "say something..." },
			{ "hahaha", "hehehe", "haha", "hehe", "hohoho", "hoho" },
			{ "im glad that you are happy", "you look pretty when u smile :)" },
			{ "ok", "OK", "Ok", "ok!", "Ok!" },
			{ "good", "nice" },
			{ ":)", ";)", ":P" },
			{ ":)", ";)", ":P" },
			{ "It's nice to meet you", "nice to meet you" },
			{ "same here", "thank you", "so nice of you" },
			{ "whats your name?", "what is your name?", "what is your name" },
			{ "You can see my name on your screen!",
					"My name is GuruChela Chatbot",
					"you can call me GuruChela Chatbot" },
			{ "ok GuruChela Chatbot", "Ok GuruChela Chatbot",
					"hi GuruChela Chatbot", "hello GuruChela Chatbot",
					"hey GuruChela Chatbot" },
			{ "yes" },
			{ "what are you doing?", "are you busy?" },
			{ "I'm busy with my work", "im doing my assignment",
					"watching movie" },
			{ "im fine", "im doing good" },
			{ "great", "nice to hear that" },
			{ "thanks", "thank you" },
			{ "most welcome", "my pleasure!" },
			{ "bye", "tada", "ttyl", "brb", "bye bye", "bubye" },
			{ "it was nice talking to u, bye!", "I wish to talk to you again!",
					"bye", "have a good day tada!" },
			{ "really?", "really!", "really", "OMG", "omg" },
			{ "yep", "yes!" },
			// Database questions and answers
			{ "what is database", "what is relational database",
					"what do you know about database",
					"tell me something about database","database" },
			{ "Data stored in tables that are associated by shared attributes (keys) "
					+ "Entity: Object, Concept or event (subject) "
					+ "Attribute: a Characteristic of an entity"
					+ " Row or Record: the specific characteristics of one entity "
					+ "Table: a collection of records  "
					+ "Database: a collection of tables Any data element (or entity) can be found in the database through the name of the table, "
					+ "the attribute name, and the value of the primary key." },
			{ "what is relational model",
					"what do you know about relational model",
					"tell me something about relational model",
					"relational model?", "relational model" },
			{ "The relational model is a type of model in which each attribute has a unique name within an entity."
					+ "All entries in the columns are examples of it. "
					+ "Each row is unique. "
					+ "Ordering of rows and columns is unimportant. "
					+ "Each position(tuple) is limited to a single entry." },
			{ "what is data model", "What is a model",
					"what do you mean by data model",
					"tell me something about data model" },
			{ "A data model is a representation of reality. It is used to define the storage and manipulation of a database."
					+ " Data models havr two components: "
					+ "Structure: the structure of data stored within"
					+ " Operations: facilities of manipulation of data" },
			{ "what is relational database systems",
					"what do you know about relational database systems",
					"tell me something about relational database systems" },

			{ "Flexibe approach to linkages between records comes the closet to modeling the complexity of spatial relationships between objecrs." },
			{ "crud", "crud", "what is crud", "what do you mean by crud",
					"tell me something about crud" },
			{ "Refers to Create, Read, Update, Delete operations at all levels : Tables, Records, Columns." },
			{ "what is database tables",
					"what do you mean by database tables",
					"tell me something about database tables",
					"database tables" },
			{ "Tables represents entities. Tables are always named in singular such as: Vehicles, Order, Grade, etc." },
			{ "what is an atttribute", "what do you mean by attribute",
					"attribute", "attribute",
					"tell me something about attribute" },
			{ "Attribute means characterstics of an entity. ex: Vehicle(VIN,color,model,make,milage), "
					+ "Student(SSN, Fname, Lname, Address)" },
			{ "what is database views", "what do you mean by database views",
					"database view", "database views", "database views",
					"Tell me something about database views" },
			{ "A view is an individuals picture of a database. It can be composed of many tables, unbeknownst to the user. It is simplification of complex data model."
					+ "It provides measure of database security." },
			{ "table indexing", "indexing", "indexing",
					"what is table indexing", "what is indexing",
					"that do you mean by table indexing",
					"tell me something about table indexing" },
			{ "An index is a means of expediting the retrieval of data. Indexes are 'built' on columns."
					+ "Indexes occupies disc space, occassionally a alot."
					+ "Indexes must be maintained by database administrator. " },
			{ "database relationships", "database relationships",
					"what do you mean by relationship in database",
					"tell me something about database relationship" },
			{ "-Ownership, -Parentage, -Assignment, -Regulation" },
			{ "database table keys", "keys", "keys", "table keys",
					"table keys", "what is a key in database",
					"what do you mean by database table keys",
					"tell me something about database table keys" },
			{ "A key of a relation is a subset of attributes with the following attributes. "
					+ "Unique identification. Non-redundancy." },
			{ "primary key", "primary key", "what is a primary key",
					"what do you mean by primary key",
					"tell me something about primary key" },
			{ "Serves as the row level addressing mechanism in relational database model. "
					+ "It can be formed by the combination of several items." },
			{ "foreign key", "foreign key", "what is a foreign key",
					"what do you mean by foreign key",
					"tell me something about foreign key" },
			{ "A column or set of columns within a table that are required to match those "
					+ "of a primary key of a second table." },
			{ "join relationship", "join relationship", "relationship",
					"relationship", "what is a join relationship",
					"what do you mean by a join relationship",
					"tell me something about join relationship" },
			{ "One to many relationships include the primary key of' one' table "
					+ "and foreign key in the' many' table" },
			{  "cardinality", "what is cardinality",
					"what do you mean by cardinality",
					"tell me something about cardinality" },
			{ "One- to - one , one - to - many, many - to - many relationships." },
			{ "optionality", "optionality", "what is optionality",
					"what do you mean by optionality",
					"tell me something about optionality" },
			{ "The relationship is either mandatory or optional." },
			{ "database integrity", "database integrity",
					"what is database integrity",
					"what do you mean by database integrity",
					"tell me something about database integrity" },
			{ "Databse integrity involves the maintenance of the logical and business rules of the database."
					+ "There are two kinds of integrity: Entity integrity and Referential integrity." },
			{  "entity integrity",
					"what is entity integrity",
					"what do you mean by entity integrity",
					"tell me something about entity integrity" },
			{ "It deals with within-entity rules. These rules deal with ranges and permission of null values in attributes or possibly between records." },
			{ "referential integrity", "referential integrity",
					"what is referential integrity",
					"what do you mean by referential integrity",
					"tell me something about referential integrity" },
			{ "Referential integrity concerns two or more tables that are related." },
			{ "sql" },
			{ "Type CREATE or DELETE or SELECT or UPDATE for respective queries." },
			{ "create" },
			{ "CREATE TABLE SALE - Item definition expression(s) - {item.type(width)}" },
			{ "delete" },
			{ "DELETE table WHERE operation" },
			{ "select" },
			{ "SELECT list FROM table WHERE condition" },
			{ "update" },
			{ "UPDATE tables -SET item = expressions. -WHERE expressions - INSERT INTO table - VALUES..." },
			{ "database normalization", "normalization",
					"what is normalization",
					"what do you mean by normalization",
					"tell me something about normalization" },
			{ "Normalization is the process of structuring data to minimize duplication and inconsistencies."
					+ "The process generally involves breaking down a table into two or many tables."
					+ " Normalization is usually done in stages, with each stage applying more rigorous rules"
					+ " to the types of information which can be stored in a table." },
			// default stuffs
			{
					"Please ask the questions properly.",
					"Is your grammar ok?",
					"Did you match the format of your question with the default?",
					"hey what are you talking about?",
					"im not getting a thing", "could u please explain a bit?",
					"stop talkin", "GuruChela Chatbot is unavailable!" }, };

	String[][] verbs = { { "is", "'re" }, { "was", "'re" },
			{ "think", " think" }, { "s", "'re" }, { "are", "'re" },
			{ "'re", "'re" } };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss);

		lv = (ListView) findViewById(R.id.listView1);

		adapter = new DiscussArrayAdapter(getApplicationContext(),
				R.layout.listitem_discuss);

		lv.setAdapter(adapter);

		editText1 = (EditText) findViewById(R.id.editText1);
		adapter.add(new OneComment(
				true,
				"Hello everyone! I'm an Artificial Intelligent Chatbot."
						+ "\n"
						+ "You can ask me any questions related to your study matters!"
						+ "\n"
						+ "Gurus can teach me and Chelas can learn from me!"
						+ "\n"
						+ "Please ask me questions in following formats:"
						+ "\n"
						+ "What is abc?"
						+ "\n"
						+ "What do you know about abc?"
						+ "\n"
						+ "Tell me something about abc?"
						+ "\n"
						+ "abc?"
						+ "\n"
						+ "Till now i have knowledge about Database Technology only!"
						+ "\n"
						+ "If you want information about SQL queries just type 'SQL'.."
						+ "\n"
						+ "My masters hope that you will find me useful!"));
		editText1.setOnKeyListener(this);

	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if ((event.getAction() == KeyEvent.ACTION_DOWN)
				&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
			// Perform action on key press
			String quote = editText1.getText().toString();
			adapter.add(new OneComment(false, quote));
			editText1.setText("");
			quote = quote.trim();
			while (quote.charAt(quote.length() - 1) == '!'
					|| quote.charAt(quote.length() - 1) == '.'
					|| quote.charAt(quote.length() - 1) == '?') {
				quote = quote.substring(0, quote.length() - 1);
			}
			quote = quote.trim();
			byte response = 0;
			// check for matches--
			int j = 0;
			while (response == 0) {
				if (inArray(quote.toLowerCase(), chatBot[j * 2])) {
					response = 2;
					int r = (int) Math.floor(Math.random()* chatBot[(j * 2) + 1].length);
					addText("" + chatBot[(j * 2) + 1][r]);
				}
				j++;
				if (j * 2 == chatBot.length - 1 && response == 0) {
					response = 1;
				}
			}
			
			// try counter---
			if (response == 1) {
				String quoteWords[] = quote.split("[ ']");
				int c = counter(quoteWords);
				if (c != -1) {
					String ext = quote.split(verbs[c][0])[1];
					addText("You" + verbs[c][1] + ext);
					response = 2;
				}
			}

			// default--
			if (response == 1) {
				int r = (int) Math.floor(Math.random()
						* chatBot[chatBot.length - 1].length);
				addText("" + chatBot[chatBot.length - 1][r]);
			}

			return true;
		}
		return false;
	}

	private boolean inArray(String in, String[] str) {
		// TODO Auto-generated method stub
		boolean match = false;
		for (int i = 0; i < str.length; i++) {
			if (str[i].equals(in)) {
				match = true;
			}
		}
		return match;

	}

	private int counter(String[] str) {
		// TODO Auto-generated method stub
		int verbID = -1;
		for (int i = 0; i < str.length; i++) {
			for (int j = 0; j < verbs.length; j++) {
				if (str[i].equals(verbs[j][0])) {
					verbID = j;
				}
			}
		}
		return verbID;

	}

	private void addText(String string) {
		// TODO Auto-generated method stub

		adapter.add(new OneComment(true, string));
	}

}