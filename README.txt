# Twitter-Query-Framework

Hingon Miu & Raymond Xia

Implementation of framework that queries Twitter for user data and delivers query results to plug-ins.

1. write the getName() method and provides the name of your plug-in.

2. write the registerFramework(Framework) method and save the Framework
   pointer in a private local variable.

3. write the getPanel() method, and it provides a pointer of a JPanel 
   created specifically for each plug-in that will be used by the
   framework GUI class for display. 

   - get the FrameworkGUI class from the Framework pointer, and get 
     the main hashtable that stores all the archived SearchResult.

   - the main hashtable that stores all the userNameToSearchResult 
     hashtables with groupName as keys, then the userNameToSearchResult 
     are hashtables that stores SearchResult with userName as keys.

          main hashtable type: 
                Hashtable<String, Hashtable<String, SearchResult>>
          sub hashtable type:
                Hashtable<String, SearchResult>

   - SearchResult class contains all the information needed to 
     construct ANY graphs. 

   - labels list is returned by calling getLabels() with the framework
     pointer. calling getText() with each JLabel pointers in the list
     with return either a groupName or userName. the complete labels list
     contains all groupName and userName JLabel pointers.

   - labels list has all the JLabel pointers in correct order.

        eg.  ___________________________________
            | G1 | U1 | U2 | U3 | G2 | U4 | G3 |...

      Group G1 has users U1, U2, and U3, while Group G2 has user U4.

   - the implementation distinguishes groupName from userName by checking
     whether the "- " char sequence is contained in the string. if the 
     "- " char sequence is not in the string, that string is a groupName.
     if the "- " char sequence is in the string, that string is a userName.

   - hence, you can extract the groupName and userName accordingly from the
     labels list, and then get the corresponding SearchResult class.

        eg. to get the SearchResult for a userName,
            call main_hashtable.get(groupName).get(userName)

   - use all the data you need.

   - at last, remember to return a JPanel pointer.

4. find the main method inisde the framework package and register the
   plugin like this => framework.registerPlugin(new SamplePlugin());
