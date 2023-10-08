# Taking and sharing notes


In this application, user can create their own notes and share them with other users. Users can also edit notes that other
users have shared with them.


Notes are identified by their title (we know, it might not be the best idea) and they have a title and content. Users are
identified by their email.


In the file *calls.http* you'll find all the possible calls to the API. Note that there are also calls that trigger exceptions and input validation errors.


You can run the file *calls.http* directly from within IntelliJ. It provides an HTTP client that behaves like Postman or other similar clients.


Also, note that we don't have any *data.sql* file to populate the database. Instead, we have added data at the *NotesRestExpertApplication* class, using the
application service.



## REST experts: TODO
1. Add the OPENAPI dependency and see the documentation of the API. You can access it at http://localhost:8080/swagger-ui.html
   Play and try the different calls. Once you add the validation errors and the exception handling, you'll see them in the documentation.
2. Add validations to the input data. The first thing you need to do is to add the appropriate annotations to the DTOs (and probably something else, somewhere else) .
   * The *title* of the NoteDTO must be between 3 and 255 characters long and must contain only letters
   * The *email* of the UserDTO must be a valid email address. Neither can be null nor empty
   * The *name* and *secondname* of the user must be 3 or more characters long and must begin with a capital letter
   * Also remember to add appropriate messages for each error
3. Handle the different exceptions.
   * UserNotFoundException and NoteDoesNotExistException --> return a HTTP not found 404 status and the exception message
   * NotEditableNote and NoteNotOwnedException --> return a HTTP conflict 409 status and the exception message
   * ErrorValidationHandlingAdvice and MethodArgumentNotValidException --> return a HTTP bad request 400 status and all the error messages
4. Add the appropriate exception handling. You need to add the appropriate annotations to the ControllerAdvice class and create the
   exception classes. You can use the *calls.http* file as a guide to the tests you need to implement.
5. Ok, let's have some fun with Jackson!!! Recall that Jackson is a library that serializes and unserializes objects from/to json format
   We propose you to uncomment the getOwner() method in the Note class and see what happens. You'll get a stack overflow error. Can you figure out why?
   Fix it using the appropriate Jackson annotations (without commenting back the getter method).
6. Add a numerical Id to the notes. Make sure the IDs are generated in the note service using the TSID library.

   