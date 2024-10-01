# Pursuit-Evasion on a Graph

## Project Overview
This project simulates a pursuit-evasion game on graph structures using Java. The game involves two players: 
- **Evader**: Aims to avoid capture.
- **Pursuer**: Attempts to capture the evader.

Two primary algorithms are used to control the players:
1. **MoveTowardsPlayerAlgorithm**: The pursuer moves towards the evader.
2. **MoveAwayPlayerAlgorithm**: The evader moves away from the pursuer.

## Features
- Simulates player strategies on various graph configurations.
- Tests different edge probabilities and graph sizes (10, 20, and 30 nodes).
- Performance metrics include capture rates and average steps to capture.

## Algorithms
- **MoveAwayPlayer1Algorithm**: Enhanced evasion strategy with foresight, mobility, and distance metrics.
- **MoveTowardsPlayer1Algorithm**: Improved pursuer strategy using shortest path estimation and penalty for revisiting vertices.

## Results Summary
- Capture rates increase with edge density in smaller graphs.
- Larger graphs introduce complexity, giving the evader more escape options, resulting in longer chase times.

## How to Run
1. Clone the repository.
2. Run the simulations:
   - For basic simulation: `run Exploration1.java`.
   - For improved evader strategy: `run driver.java` with `MoveAwayPlayer1Algorithm`.
   - For improved pursuer strategy: `run driver.java` with `MoveTowardsPlayer1Algorithm`.

## Acknowledgements
Special thanks to Sadir, Prof. Bender, and Prof. Harper for their support and guidance.
