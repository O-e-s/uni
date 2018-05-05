# SolutionRepresention
Store array of city IDs

## clone
- new SolutionRepresention
- set solution as copy of current solution

# TSPInstance
## createSolution
- new SolutionRepresention
- random permutation of city IDs, eg
  - create a list of 0 to numberofCities -1
  - shuffle that list
  - convert to whatever your solution rep is (recommend Integer[])
- set representation
- return new TSPSolution
  - calc object func val
  - pass numberofCities

# TSPObjectiveFunction
## getCost
- Get two locations using instance.getLocationForCity
- Apply Euclidean distance equation
  - Math.pow

## getObjectiveFunctionValue
- representation is a Hamiltonian cycle
- loop through each city
- add cost of travelling from current city to next
- make sure you use the modulo so you can wrap around the array
  - need to include first and last cities

# TSPInstanceReader
- Take a look at format for instances
- you only need DISTANCE and NODE_COORD_SECTION
- consume until next line with Scanner.nextLine() to ignore lines you don't need
- initialise locations to new Location[DIMENSION]
- read Locations until EOF or for DIMENSION locations

# G52AIMTSP
- Initialise heuristics in the construction
- hardcode getNumberOfHeuristics since it's used by superclass constructor

psyjs17@nott...
