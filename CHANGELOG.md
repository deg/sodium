# Change Log

## [Unreleased]
### Changed
_(nothing)_
### Added
- Add `utils/ci-compare`, `utils/ci-sort` and `utils/ci-include?`
- Add `nax/draw-tags`, `nax/tag-adder`, and `nax/tag-selector`
- Add `nax/google-ad`
- Add `utils/sub2`
### Removed
_(nothing)_
### Fixed
- Removed over-eager error checking from `<sub`. It was less useful than re-frame's internal reporting

## [0.6.1]
### Changed
- Updated dependencies: clojure, clojurescript

## [0.6.0]
### Changed
- Updated dependencies: re-frame, lein-doo
### Added
- Support :on-click in all components
### Fixed
- Correctly handle true value from dom

## [0.5.0]- 2017-10-02
### Added
- Added components advertisement, divider, icon, icon-group, menu-header, menu-menu, segment, segment-group

## [0.4.0] - 2017-09-28
### Added
- Some doc/readme improvements
- `:style` accepted in components
- `>events` for multiple dispatch for on-* handlers
### Fixed
- Fixed build problem; added missing dependency

## [0.3.0] - 2017-09-26
### Added
- Added components modal, modal-header, modal-content, modal-description, modal-actions


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


[Unreleased]: https://github.com/deg/sodium/compare/5ecf157...HEAD
[0.6.1]: https://github.com/deg/sodium/compare/bb64849...5ecf157
[0.6.0]: https://github.com/deg/sodium/compare/a1dd09e...bb64849
[0.5.0]: https://github.com/deg/sodium/compare/0dd1e35...a1dd09e
[0.4.0]: https://github.com/deg/sodium/compare/17de322...0dd1e35
[0.3.0]: https://github.com/deg/sodium/compare/043f00a...17de322
[0.2.0]: https://github.com/deg/sodium/compare/6c372df...043f00a
[0.1.0]: https://github.com/deg/sodium/compare/ff21e14...6c372df
