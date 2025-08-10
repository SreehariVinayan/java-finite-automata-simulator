# Java Finite Automata Simulator

[![Java](https://img.shields.io/badge/Java-8+-blue.svg)](https://www.oracle.com/java/)  
A Java-based simulator for **Deterministic Finite Automata (DFA)** and **Non-deterministic Finite Automata (NFA)** — including support for **ε-transitions**.  
The simulator can read automata definitions from CSV files or run from default hardcoded examples, evaluate all possible strings up to a given length, and display **accepted** and **rejected** strings.

---

## Features
- Supports **DFA** and **NFA** evaluation
- Handles **epsilon transitions** in NFA
- Reads DFA definitions from CSV files
- Runs NFA using built-in hardcoded example (CSV support coming soon)
- Configurable **maximum string length**
- Bulk evaluation of all possible strings
- Console output for **Accepted** and **Rejected** strings

---

## Repository Structure
```
.
├── src/
│   └── logic/
│       ├── Main.java          # DFA entry point
│       ├── DFA.java           # DFA logic
│       ├── State.java         # DFA state representation
│       ├── NFA.java           # NFA logic
│       ├── NfaState.java      # NFA state representation
│       ├── FileHandler.java   # CSV file reader
│       ├── Tuple.java         # Utility tuple class
│
├── files/
│   ├── dfa_alternateAB.csv    # Example DFA
│
├── README.md
├── .gitignore
```

---

## CSV Format

### DFA CSV Structure
Each row represents a **state**.  
- **First column:** State label (e.g., `q0`)  
- **Middle columns:** Next states for each alphabet symbol in order  
- **Last column:** `"TRUE"` if final/accepting state, otherwise `"FALSE"`  

Example (`dfa_alternateAB.csv`):
```
4,a,b,final
q0,q1,q0,FALSE
q1,q1,q2,FALSE
q2,q3,q0,FALSE
q3,q1,q0,TRUE
```

Here:
- Alphabet = `{a, b}`
- Maximum string length = `4`
- `q3` is the only accepting state

---

## Usage

### 1. Compile
```bash
javac -sourcepath src -d out src/logic/*.java
```

### 2. Create JAR
```bash
jar cfe DFA.jar logic.Main -C out .
```
*(For NFA, replace `logic.Main` with `logic.NFA`)*

---

### 3. Run DFA

**From class files:**
```bash
java -cp out logic.Main <file_name> <max_combination_length>
```

**From JAR:**
```bash
java -jar DFA.jar <file_name> <max_combination_length>
```

---

### 4. Run NFA (Default Example)

**From class files:**
```bash
java -cp out logic.NFA <max_combination_length>
```

**From JAR:**
```bash
java -jar NFA.jar <max_combination_length>
```

---

- **First argument** = CSV file path (for DFA) or max string length (for NFA default)  
- **Second argument** (DFA only) = Maximum string length

## Example Output
```
File path set to dfa_alternateAB.csv
Maximum Combination length set to 2

Accepted Strings
<Empty String>
a
b
ab
ba

Rejected Strings
aa
bb
```

---

## Contributing
1. Fork the repository
2. Create a new branch (`git checkout -b feature-name`)
3. Commit changes (`git commit -m 'Add feature'`)
4. Push branch (`git push origin feature-name`)
5. Open a Pull Request

---
