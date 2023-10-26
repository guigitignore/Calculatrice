# Calculatrice

## Building

`make`

## Running

__Basic usage:__ `java -jar out/main.jar`

__Options:__

- remote (open a TCPServer on port 1111 by default)
- remote shared (share PileRPL with all remote sessions)
- log (record local session)
- replay (replay saved session)
- remote replay (replay session remotly)

__Note:__ log cannot be associated with remote or replay.

__Example:__ `java -jar out/main.jar remote shared replay`
