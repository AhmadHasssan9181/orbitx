#! /bin/bash

# Copyright (c) 2026 Orbit Team.
#
# SPDX-License-Identifier: AGPL-3.0-only.
# Please see LICENSE files in the repository root for full details.

# Format is:
# orbitx://open/{sessionId} to open a session
# orbitx://open/{sessionId}/{roomId} to open a room
# orbitx://open/{sessionId}/{roomId}/{eventId} to open a thread

# Open a session
# adb shell am start -a android.intent.action.VIEW -d orbitx://open/@alice:orbitweb.tech
# Open a room
adb shell am start -a android.intent.action.VIEW -d orbitx://open/@alice:orbitweb.tech/!dehdDVSkabQLZFYrgo:orbitweb.tech
