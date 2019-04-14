# OnlineBankingPortal

Jaldi kaam karo boiis, 20th submission hai

## Using the changeapi package

The changeapi package included in the model package is used to create Observables from the classes in the model class.

To use it, the only requirement is for the class to extend ChangeableBase and to call the notifyListeners() method in all the setters. (See LoginCredentials (in the model package) for an example)

## Notes
* All "Local" classes, (Like LocalConnection) are used for testing
