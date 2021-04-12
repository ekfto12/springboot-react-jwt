import React, { Component } from 'react';
import LoginApp from './components/jwtLogin/LoginApp'
import './App.css';
import './bootstrap.css';
import JJ from './components/jwtLogin/AuthenticationService'

class App extends Component {
  componentDidMount() {
    JJ.executeRefresh();
  }

   

  render() {
    return (
      <div className="App">
         <LoginApp/>
      </div>
    );
  }
}
export default App;