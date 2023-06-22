import React from 'react'
import { Link } from 'react-router-dom'
//https://getbootstrap.com/docs/5.3/components/navbar/#how-it-works
import { logOutUser } from '../sessions/Session'


function logOut(){
logOutUser()
}

export default function Navbar() {
  return (
    <div>

      <nav className="navbar navbar-expand-lg navbar-light bg-warning">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/index ">Choco store</Link>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>

          {/* <Link className="btn btn-outline-light" to="/adduser">Add User</Link> */}
          <Link className="btn btn-outline-dark" to="/" onClick={() => logOut()}>Log out</Link>


        </div>
      </nav>

    </div>
  )
}
