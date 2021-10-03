ShelfLife - Eoghan Martin - C18342116

Classes:

Item Class - This class holds all the attributes that will be in an item and it is used when creating new items, it includes getters and setters and a constructor method.

Database Helper is the class that creates the database table that is needed and it extends SQLiteOpenHelper. Attributes for each row are used here to create a local database table on the device.

The DatabaseManager class has a number of methods that will get items in the database for displaying or will edit items or delete them from the database. There are three methods for retrieving items for display
getAllItems, getItemsByType, and getItem. The first will return all items in the database, the second will return items of a certain type, and the third will return one single item. Other methods include insertItem, editItem and deleteItem.

Main Activity class is the class that shows the users the categories of items and allows them to see their items and add new items

ListItemActivity class allows users to see the items they have in the selected category

ItemInfoActivity class allows the user to see more info of a certain item

EditItemActiviy class allows the users to edit or delete an item

AddItemActivity class allows the users to add new items to the db.

The application works as follows:

Main Activity is what comes up first when you open the application. It extends ListActivity and displays a predefined list of three Strings Fridge, Pantry and other. When clicked An intent is made that will go to ListItemsActivity. This page also has an add button that takes the user AddItemActivity

ListItemActivity is the activity that displays a list of items depending on what the user has clicked from the previous activity, it uses a custom adapter that will show the item name, the expiry and an info image. When the user clicks the item it will bring them to the ItemInfoActivity

ItemInfoActivity is the activity that displays the items info that the user clicks on, including name, expiry date, date added, the type of item, the item description. You can also edit or delete this item from this screen. If you choose edit it will take you to the EditItemActivity. If you click delete an alert dialog will pop up confirming
you want to delete this item.

EditItemActivity, in this activity the items info will be passed and you can edit that item. If you press the back button it will ask are you sure you want to go back without saving changes. You can also delete the item and if you press save the item will be saved.

AddItemActivity will start when the user clicks add on the MainActivity. This allow users to add new items to any of the categories. Once the save button is pressed it will add this item to the database but it also asks the user does it want to add this item to calendar. If the user says yes it will add an event in calendar.

If I had more time I would have added an update event method in the EditItemActivity. Because I couldn't get it to work on the emulator I never got to updated the event. As well as this I would have liked to have
had some sort of countdown function that showed a time remaining for items.
