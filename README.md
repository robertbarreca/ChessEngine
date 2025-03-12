# Java Chess Engine

A fully object-oriented Chess Engine built in Java, featuring a modular design with move generation, player management, and GUI components. The project is structured for clarity, testability, and extensibility.

![Chess Engine Demo](/art/demo.gif)

## 🧠 Features

- Complete chess rules implementation
- Legal move generation and validation
- Support for castling, en passant, and pawn promotion
- Turn-based player handling
- Object-oriented design for boards, pieces, moves, and players
- AI opponent using the Minimax algorithm with alpha-beta pruning
- GUI for interactive gameplay
- Fen Parser to create chess boards from FEN strings and vice versa
- JUnit-based test suite

## 🚀 Getting Started

### Prerequisites

- Java JDK 8 or higher
- JUnit 4.13.2 (included in `lib/`)

### Compile and Run

#### Easy using the provided makefile:
```bash
make            # Compile the entire project
make run        # Run the Chess Engine
make test       # Run test suite
make clean      #remove all compiled files
```

## ✅ Testing

This project uses JUnit for testing. Test Coverage includes:
- Check, checkmate, and stalemate detection
- Various utility functions used for the creation of the project
- Creating board's from FEN Strings
- Move generation
- Piece Movement validation
- Castling logic
- Minimax algorithm behavior

