/**
 * Created by joco on 19/06/2017.
 */


// var React = preactCompat,
//     ReactDOM = preactCompat;

var h = React.createElement

var mkTreeComp2 = function (name, indent, isLeaf, childCmpConstr) {
    return React.createClass({

        render: function () {
            var par = this.props.parentsName

            var myName = par + '/' + name + this.props.chId
            console.log(indent + myName + ' start')


            var me = '' + indent + myName

            if (!isLeaf) {
                var res = h('div', {}, me,
                    h(childCmpConstr, {parentsName: myName, chId: 1}),
                    h(childCmpConstr, {parentsName: myName, chId: 2})
                )
            }
            else {
                var res = h('div', {}, me)
            }

            console.log(indent + myName + ' end')

            return (res)
        },
    });
}

var cache=0

var mkTreeComp = function (name, indent, isLeaf, childCmpConstr) {
    return class Comp extends React.Component {

        render() {
            cache=cache+1
            var par = this.props.parentsName

            var myName = par + '/' + name + this.props.chId

            console.log(indent + myName + ' start')


            var me = '' + indent + myName+' '+cache

            if (!isLeaf) {
                var res = h('div', {}, me,
                    h(childCmpConstr, {parentsName: myName, chId: 1}),
                    h(childCmpConstr, {parentsName: myName, chId: 2})
                )
            }
            else {
                var res = h('div', {}, me)
            }

            console.log(indent + myName + ' end')

            return (res)
        }

        log(s) {
            var par = this.props.parentsName

            var myName = par + '/' + name + this.props.chId
            console.log(indent + myName + ' '+s)
        }

        componentWillMount() {
            this.log('will mount')
        }


        componentDidMount() {
            this.log('did mount')
        }

        componentWillReceiveProps () {
            this.log('will receive props')
        }
    }
}


var CompAConstr = mkTreeComp('A', '__', false,
    mkTreeComp('B', '____', false,
        mkTreeComp('C', '______', false,
            mkTreeComp('D', '______', false,
                mkTreeComp('E', '________', true)))))

// mkLeafCompConstr('D', '________'))))


console.log("before var rootElement")

var rootElement =
    React.createElement('div', {},
        React.createElement(CompAConstr, {parentsName: "root", chId: 1}, "Contacts")
    );

console.log("after var rootElement")

console.log("render start")
ReactDOM.render(rootElement, document.getElementById('react-app'))
console.log("render end")
console.log(CompAConstr)
console.log(cache)

console.log("update 1 start")
ReactDOM.render(rootElement, document.getElementById('react-app'))
console.log("update 1 end")
