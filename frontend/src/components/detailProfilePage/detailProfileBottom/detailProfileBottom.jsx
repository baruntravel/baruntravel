import { Drawer } from "antd";
import React, { useCallback, useState } from "react";
import { useRecoilState, useResetRecoilState } from "recoil";
import { onLogout } from "../../../api/authAPI";
import { userState } from "../../../recoil/userState";
import LogoutConfirm from "../../common/logoutConfirm/logoutConfirm";
import ProfileEdit from "../../common/profileEdit/profileEdit";
import styles from "./detailProfileBottom.module.css";

const DetailProfileBottom = (props) => {
  const [openLogout, setOpenLogout] = useState(false);
  const [openEditProfile, setOpenEditProfile] = useState(false);
  const [userStates, setUserStates] = useRecoilState(userState);

  const onOpenLogout = useCallback(() => {
    setOpenLogout(true);
  }, []);
  const onCloseLogout = useCallback(() => {
    setOpenLogout(false);
  }, []);
  const onOpenEditProfile = useCallback(() => {
    setOpenEditProfile(true);
  }, []);
  const onCloseEditProfile = useCallback(() => {
    setOpenEditProfile(false);
  }, []);
  const onUpdateUserProfile = useCallback(
    (data) => {
      const { email, name, avatar } = data;
      setUserStates((prev) => {
        const updated = { ...prev, email, name, avatar };
        return updated;
      });
    },
    [setUserStates]
  );
  const resetList = useResetRecoilState(userState);
  const onHandleLogout = useCallback(() => {
    resetList();
    onLogout();
  }, [resetList]);
  return (
    <div className={styles.DetailProfileBottom}>
      <span onClick={onOpenEditProfile}>설정</span>
      <span onClick={onOpenLogout}>로그아웃</span>
      {openLogout && (
        <LogoutConfirm onLogout={onHandleLogout} onClose={onCloseLogout} />
      )}
      <Drawer
        placement="right"
        closable={false}
        visible={openEditProfile}
        width={"100vw"}
        bodyStyle={{ padding: 0 }}
      >
        <ProfileEdit
          userStates={userStates}
          onClose={onCloseEditProfile}
          onUpdateUserProfile={onUpdateUserProfile}
        />
      </Drawer>
    </div>
  );
};

export default DetailProfileBottom;
