
# This is the proposed implementation for the 'fetcher-application'

### Running the application

Please go to root folder and run

```sh
$ sbt run

```


Application should be available at port 9000. Port number is configurable. You change it inside `application.conf`


The interval in which application goes to Web in order to fetch items is also configurable. Please look  `application.conf` , 'interval ' attribute.


### Using the application

We basicaly have two functionalities:
* Web data fetching
* Item querying given an index

 First item is a daemon service, it runs periodically, without the need of user interaction.
 Second item is available at the following exposed rest endpoint:
  - <root>:<port>/item/<index>

  Example : http://localhost:9000/item/10  -> Will retrieve item which is stored at position number 10 in our internal cache.

  <index> is a valid positive integer

  Expected result :
   - In case we find an item, the item itself is returned, status code 200
   - In case no item is found, status code 400 is returned with following message : No item found for the given index



### What should I improve

- More tests, I just unit tested my services. It would be nice to have a sort of functional test consuming my endpoint, and, hence, testing the solution end-to-end
- Error handling
- Improve loggings
