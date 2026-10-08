# Use of Maps

<!--- TOC -->

* [Overview](#overview)
* [Local Development with MapTiler](#local-development-with-maptiler)
* [Making Releasable Builds](#making-releasable-builds)
* [Custom Map Providers](#custom-map-providers)

<!--- END -->

## Overview

OrbitX Android uses [MapTiler](https://www.maptiler.com/) to provide map imagery for location sharing.

## Local Development with MapTiler

Place your API key in `local.properties`:

```properties
services.maptiler.apikey=your-api-key
```

Optionally set custom style IDs:
```properties
services.maptiler.lightMapId=your-light-style
services.maptiler.darkMapId=your-dark-style
```

## Making Releasable Builds

Set the environment variables:
- `ORBIT_ANDROID_MAPTILER_API_KEY`
- `ORBIT_ANDROID_MAPTILER_LIGHT_MAP_ID`
- `ORBIT_ANDROID_MAPTILER_DARK_MAP_ID`

## Custom Map Providers

To use an alternative map provider, implement `TileServerStyleUriBuilder` and `StaticMapUrlBuilder` in `features/location/api`.
