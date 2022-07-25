To run on your machine:
* clone this repository
* open terminal in this directory
* set your API key with `export NASA_API_KEY=<YOUR API KEY>`
* setup the dev env with `make dev`
* run tests with `make test`



To clear the env with `make clean`



Comments: 
   * Assuming "closeApproachDataItem.getMissDistance().getKilometers()" is the parameter that shows distance to earth
   * Configured simple caching (without TTL), that covers only exact request, so if request params are same then it will hit cache. in other case it will not hit cache(to improve this more complex solution need to be developed)
   * Provided only general tests: Integration test with nasa API and some unit tests. Edge cases are not covered, and e2e are also if not full.
   * Tests with wiremock (without actual NASA API) are not provided