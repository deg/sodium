# Change Log

## [Unreleased]
### Changed
- `<sub` now takes optional `default` parameter and warns better
- Now using Soda-ash 0.4.0 (and Semantic UI 2.2.12; update your CSS)
- Now using re-frame 0.10.1
- Now using ClojureScript 1.9.908
### Added
- *Sodium core*. Add button, grid-column, grid-row image, label, list-na, list-item, and search.
- *Sodium extensions*. Components that specialize the core components. So far:
   app-title, and labelled-field.
- *re-frame utilities*. Added `vec->fn` and `event->fn`.
- *Chrome utilities*. Browser support. So far: 'console-dir'.

## [0.1.0] - 2017-08-13
### Added
- Initial project
- Some Soda-ash components imported: 'checkbox', 'container', 'dropdown', 'form',
  'form-button', 'form-input', 'form-group', 'grid', 'header', 'input', 'menu',
  'menu-item', 'rail', and 'text-area'.
- Helpers for data in/out of components: '>event', '>atom', and '<atom'.
- Minor helper functions: `<sub`, `>evt`, etc.
### Changed
_(nothing)_
### Removed
_(nothing)_
### Fixed
_(nothing)_

[Unreleased]: https://github.com/deg/sodium/compare/6c372df...HEAD
[0.1.0]: https://github.com/deg/sodium/compare/ff21e14...6c372df
