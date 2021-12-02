# Advent of Code 2021

My solutions for [Advent of Code 2021](https://adventofcode.com/2021).

## Running

Each day's solution is in a separate Clojure namespace with an executable `-main` function.

To run a given day:

```shell
$ clj -M -m dayXX
```

## Testing

The unit tests use the standard [clojure.test] and are run using Cognitect's [test-runner].

To run the tests for all days:

```shell
$ clj -X:test
```

To run the tests for a specific day:

```shell
$ clj -X:test :nses [dayXX-test]
```


[clojure.test]: https://clojure.github.io/clojure/clojure.test-api.html
[test-runner]: https://github.com/cognitect-labs/test-runner
