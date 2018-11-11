import React , { Component } from "react";

class Counter extends Component {

   /* constructor() {
        super();
        this.incrementClick = this.incrementClick.bind(this);
    }*/



    render () {

        return (
        <div>
            <span className={this.getBadgeClasses()}>{this.calculateCounter()}</span>
            <button onClick={() => this.props.onIncrement(this.props.counter)} className="btn btn-secondary btn-sm m-2">Increment</button>
            <button onClick ={() => this.props.onDelete(this.props.counter.id)} className="btn btn-danger btn-sm m-2">Delete</button>
        </div>
        );
    }

    getBadgeClasses() {
        let element = "badge m-2 badge-"
        element+= this.props.counter.value===0?"warning":"primary";
        return element;
    }

    calculateCounter() {
        return this.props.counter.value===0?"zero":this.props.counter.value;
    }

}

export default Counter;