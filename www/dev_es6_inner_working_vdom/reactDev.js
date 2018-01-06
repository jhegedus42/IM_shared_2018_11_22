/**
 * Created by joco on 19/06/2017.
 */

ChromeSamples = console
// A base class is defined using the new reserved 'class' keyword
class Polygon {
    // ..and an (optional) custom class constructor. If one is
    // not supplied, a default constructor is used instead:
    // constructor() { }
    constructor(height, width) {
        this.name = 'Polygon';
        this.height = height;
        this.width = width;
    }

    // Simple class instance methods using short-hand method
    // declaration
    sayName() {
        ChromeSamples.log('Hi, I am a ', this.name + '.');
    }

    sayHistory() {
        ChromeSamples.log('"Polygon" is derived from the Greek polus (many) ' +
            'and gonia (angle).');
    }

    // We will look at static and subclassed methods shortly
}
// Classes are used just like ES5 constructor functions:
let p = new Polygon(300, 400);
p.sayName();
ChromeSamples.log('The width of this polygon is ' + p.width);

let {h, render, Component} = preact;


class List extends Component {
    constructor(...args) {
        var _temp;

        return _temp = super(...args), this.render = () => {
            return h(
                "ul",
                null,
                this.props.items.map(function (item) {
                    return h(
                        "li",
                        {key: item},
                        item
                    );
                })
            );
        }, _temp;
    }

}

class FilteredList extends Component {
    constructor() {
        super();

        this.filterList = event => {
            var updatedList = this.state.initialItems;
            updatedList = updatedList.filter(function (item) {
                return item.toLowerCase().search(event.target.value.toLowerCase()) !== -1;
            });
            this.setState({
                items: updatedList
            });
        };

        this.componentWillMount = () => {
            this.setState({
                items: this.state.initialItems
            });
        };

        this.render = () => {
            return h(
                "div",
                null,
                h("input", {type: "text", placeholder: "Search", onChange: this.filterList}),
                h(List, {items: this.state.items})
            );
        };

        this.state = {
            initialItems: ["California", "New York"],
            items: []
        };
    }
}

// Start 'er up:
render(h(FilteredList, null), document.getElementById("react-app"));

