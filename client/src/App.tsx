import React from 'react';
import './App.css';

const Footer = () => {
    return <>
        <p>This is Stream Line by Hadrien Renaud</p>
        <p>Check this out on <a href="https://github.com/HadrienRenaud/simple-spring-graphql">Github</a></p>
    </>
};

const App: React.FC = () => {
    return (
        <div className="App">
            <div app-content>
                Coucou
            </div>

            <div className="footer">
                <Footer/>
            </div>
        </div>
    );
}

export default App;
