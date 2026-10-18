#!/usr/bin/env sh

exec java -cp './lib/*' org.apache.derby.drda.NetworkServerControl start -h 127.0.0.1 -p 1527


