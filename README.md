# Baumpfleger

This is an experimental Reagent component for editing hierarchical parameter values. It is by far neither complete nor directly usable but may serve as an example or base for other projects. It lacks mainly a better editing logic, additional types of edit elements (the `input` HTML elements in the leaves) and more sophisticated validation logic. 


# Motivation

The original purpose for this component is an other project where parameter lists have to be generated based on nested Java beans holding various parameters. Because the Java project is heavily in flux (adding and removing of parameter classes), the tree descriptor for the Baumpfleger component could be dynamically generated using Reflection, enabling easy processing of the currently available property values.


## Setup

To get an interactive development environment run:

    lein figwheel

and open your browser at [localhost:3449](http://localhost:3449/).
This will auto compile and send all changes to the browser without the
need to reload. After the compilation process is complete, you will
get a Browser Connected REPL. An easy way to try it is:

    (js/alert "Am I connected?")

and you should see an alert in the browser window.

To clean all compiled files:

    lein clean

To create a production build run:

    lein do clean, cljsbuild once min

And open your browser in `resources/public/index.html`. You will not
get live reloading, nor a REPL. 

## License

Copyright © 2014 Marcus Spiegel

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
