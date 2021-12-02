# Advent of Code 2021

My solutions for [Advent of Code 2021](https://adventofcode.com/2021)

## Running

Each day's solution is in a separate Clojure namespace with an executable `-main` function. To run a given day:

```shell
$ clj -M -m dayXX
```

To run all the `clojure.test` unit tests:

```shell
$ clj -X:test
```

To run the unit tests for a specific day:

```shell
$ clj -X:test :nses [dayXX-test]
```
