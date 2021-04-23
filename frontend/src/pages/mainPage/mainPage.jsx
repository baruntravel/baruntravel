import React, { useState } from "react";
import PortalAuth from "../../containers/portalAuth/portalAuth";

const MainPage = (props) => {
  const [portalClicked, setPortalClicked] = useState(false);
  const [loginClicked, setLoginClicked] = useState(true);
  const handlePortal = () => {
    setPortalClicked(!portalClicked);
  };
  const handleLoginClicked = () => {
    setLoginClicked(!loginClicked);
  };
  return (
    <div>
      <button onClick={handlePortal}>로그인 버튼</button>
      {portalClicked && (
        <PortalAuth
          loginClicked={loginClicked}
          onClose={handlePortal}
          onClickChange={handleLoginClicked}
        />
      )}
    </div>
  );
};

export default MainPage;
