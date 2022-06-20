# From consumer view
## Case 1
- create by self; consume by self; close by self;
## Case 2
- create by producer; consume by self or others(by func return, parameter or field); close by self or others;
## Case 3
- create by producer; consume by self or others(by func return, parameter or field); close by producer;

# From producer view
## Case 1
- create by self; consume by self; close by self;
## Case 2
- create by self; consume by consumers(by func return, parameter or field); close by a consumer;
## Case 3
- create by self; consume by consumers(by func return, parameter or field); close by self;

# Pattern
- Close by consumer(Application layer). Consumer must close it once it has been created.
- Leave open by consumer(Framework or util layer). Consumer should not close it.
- Close by producer. Producer must close it once it has been created. Consumer's close should not really close it(StreamUtils.nonClosing).