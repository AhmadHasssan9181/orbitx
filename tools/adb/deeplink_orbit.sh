#! /bin/bash

# Copyright (c) 2026 Orbit Team.
#
# SPDX-License-Identifier: AGPL-3.0-only.
# Please see LICENSE files in the repository root for full details.

adb shell am start -a android.intent.action.VIEW \
   -d "https://orbitweb.tech/#/room/#orbit:orbitweb.tech"
