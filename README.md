# Sodium

A wrapper around [soda-ash](https://github.com/gadfly361/soda-ash)
and [semantic-ui-react](https://github.com/Semantic-Org/Semantic-UI-React) that is
friendlier to re-frame.

Sodium is still an early work in progress. I am using it to help me with other projects.
It will only grow as it helps me (or other contributers) accomplish what they need. If
you need features now, PRs are welcome.

Sodium offers the following:

# Cleaner inter-op between Semantic-UI and [re-frame](https://github.com/Day8/re-frame)

`sodium.core/>event` and `sodium.core/>atom` - Create `:on-*` handlers to pass a value
to a re-frame event or react atom.

`sodium.core/<atom` - Get a value from a react atom.

`sodium.core/defcontrol` and `sodium.core/def-simple-control` Define a control;
typically a thin wrapper around the Soda-ash control, to give parameter checking,
etc. (see below).  Soon, Sodium will contain definitions wrapping all the Soda-ash
(Semantic-UI) controls, but we are not there yet.


# Clojure-friendly naming conventions

Controls and parameters in kebab-case (`:on-change`) rather than JavaScript-friendly
camel-case (`onChange`).

# Parameter type-checking

For now, just starting to check if parameters are valid. This is a bit of a mixed
blessing, because I've not yet found a definitive list of all the valid parameters. So,
this may be overly restrictive.

I will, soon, add an option to partially disable checking. But, NYI. For now, you can
call the Soda-ash function directly. `>event` and friends will work just fine.

# A few helpful utility functions

## In `sodium.utils`
- `validate` - Wrapper for Clojure specs checking in pre-conditions.
- "Camelize" functions - Convert Clojure-style names to JavaScript style

## In `sodium.re-utils`
- `<sub` and `>evt` - Re-frame wrappers, taken from <https://lambdaisland.com/blog/11-02-2017-re-frame-form-1-subscriptions>

## In `sodium.chars`

Definitions for a few common Unicode characters.


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
