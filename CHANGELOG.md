# Change Log

## [Unreleased]
### Changed
_(nothing)_
### Added
- *Sodium core*
  - Added components modal, modal-header, modal-content, modal-description, modal-actions
### Removed
_(nothing)_
### Fixed
_(nothing)_


## [0.2.0] - 2017-09-04
### Changed
- `<sub` now takes optional `default` parameter and warns better
- Now using Soda-ash 0.4.0 (and Semantic UI 2.2.12; update your CSS)
- Now using re-frame 0.10.1
- Now using ClojureScript 1.9.908
### Added
- *Sodium core*.
  - Added components: button, grid-column, grid-row image, label, list-na, list-item, and search.
  - Add spec def: sodium/size
- *Sodium extensions*. Components that specialize the core components. So far:
   app-header, panel-header panel-subheader, section-header, section-subheader,
   and labelled-field.
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


[Unreleased]: https://github.com/deg/sodium/compare/043f00a...HEAD
[0.2.0]: https://github.com/deg/sodium/compare/6c372df...043f00a
[0.1.0]: https://github.com/deg/sodium/compare/ff21e14...6c372df
