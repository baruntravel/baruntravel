import React from "react";

const UsersRoutePage = ({ match }) => {
  const location = match.params.id;

  return (
    <div>
      <h1>{location}</h1>
      <h2>Users Routes Page</h2>
    </div>
  );
};

export default UsersRoutePage;
