# Sodium

A wrapper around [soda-ash](https://github.com/gadfly361/soda-ash)
and [semantic-ui-react](https://github.com/Semantic-Org/Semantic-UI-React) that is
friendlier to re-frame.

Sodium is still an early work in progress. I am using it to help me with other projects.
It will only grow as it helps me (or other contributers) accomplish what they need. If
you need features now, PRs are welcome.

Sodium offers the following:

# Cleaner inter-op between Semantic-UI and [re-frame](https://github.com/Day8/re-frame)

These are all defined in `sodium.core`

... >event
... >atom


# Clojure-friendly naming conventions

Controls and parameters in kebab-case (`:on-change`) rather than JavaScript-friendly
camel-case (`onChange`).

# Parameter type-checking

... pending.  For now, just starting to check if parameters are valid.

# A few helpful utility functions

## In `sodium.utils`
- `validate` - Wrapper for Clojure specs checking in pre-conditions.
- "Camelize" functions - Convert Clojure-style names to JavaScript style

## In `sodium.re-utils`
- `<sub` and `>evt` - Re-frame wrappers, taken from <https://lambdaisland.com/blog/11-02-2017-re-frame-form-1-subscriptions>

## In `sodium.chars`
- `sodium.chars\em-dash` - Unicode em-dash character



## Useful links (for now)

- https://react.semantic-ui.com/introduction
- https://github.com/Semantic-Org/Semantic-UI-React



## Usage

### Running

I have not yet released Sodium to Clojars. I've only tested running this library within
other projects. Sym-link to this project from the checkouts directory of your
project. (See the
[Leiningen notes](https://github.com/technomancy/leiningen/blob/master/doc/TUTORIAL.md#checkout-dependencies)
for a discussion of this feature).  If you are me, a working example is at
~/Documents/git/projects/receipts/client/checkouts.

You will need to include a dependency `[com.degel/sodium "0.1.0-SNAPSHOT"]` and `require`
- `[sodium.core :as na]` - Most of Sodium's functionality
- `[sodium.chars :as chars]` - Minor Unicocde support
- `[sodium.utils :refer [<sub >evt]]` - Useful utilities


You will also need to follow the instruction in
the [soda-ash README](https://github.com/gadfly361/soda-ash), particularly to include
the stylesheet and dependency.

### Testing

`lein doo phantom test auto` will run the few unit tests I've written so far.

## License

Licensed under the Eclipse Public License.

Copyright © 2017, David Goldfarb <deg@degel.com>
