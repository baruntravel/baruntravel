import React, { useCallback, useEffect, useRef, useState } from "react";
import styles from "./profileEdit.module.css";
import Avatar from "antd/lib/avatar/avatar";
import { UserOutlined } from "@ant-design/icons";
import { memo } from "react";
import { onEditProfile, onGetMe } from "../../../api/authAPI";
import useInput from "../../../hooks/useInput";
import Loading from "../loading/loading";

const ProfileEdit = memo(({ userStates, onClose, onUpdateUserProfile }) => {
  const imageInputRef = useRef();

  const [loading, setLoading] = useState(false);
  const [updateImageUrl, setUpdateImageUrl] = useState();
  const [updateImageFile, setUpdateImageFile] = useState(null);
  const [nickname, onHandleNickname, setNickName] = useInput("");

  const onChangeImage = useCallback((e) => {
    const file = e.target.files[0];
    if (file) {
      setUpdateImageFile(file);
      setUpdateImageUrl(URL.createObjectURL(file));
    }
  }, []);
  const onClickImageUpload = useCallback(() => {
    imageInputRef.current.click();
  }, [imageInputRef]);
  const onClickEdit = useCallback(
    async (e) => {
      e.preventDefault();
      setLoading(true);
      const isAvatarChange = updateImageFile ? true : false; // 업데이트 된 파일이 있다면 바뀌었다.
      const formData = new FormData();

      formData.append("name", nickname);
      formData.append("avatarChange", isAvatarChange);
      updateImageFile && formData.append("avatar", updateImageFile);
      const result = await onEditProfile(formData);
      if (result) {
        const data = await onGetMe();
        onUpdateUserProfile(data);
        setLoading(false);
        setUpdateImageFile(false);
      }
    },
    [nickname, onUpdateUserProfile, updateImageFile]
  );

  useEffect(() => {
    userStates.avatar && setUpdateImageUrl(userStates.avatar);
    userStates.name && setNickName(userStates.name);
  }, [setNickName, userStates]);

  if (loading) {
    <>
      <Loading />
    </>;
  }
  return (
    <div className={styles.ProfileEdit}>
      <header className={styles.header}>
        <div onClick={onClose}>뒤로 가기</div>
      </header>
      <section className={styles.body}>
        <div className={styles.avatar} onClick={onClickImageUpload}>
          <Avatar
            src={updateImageUrl || ""}
            size={96}
            style={{
              border: "2px solid white",
            }}
            icon={<UserOutlined />}
          />
        </div>
        <input
          type="file"
          name="image"
          hidden
          ref={imageInputRef}
          onChange={onChangeImage}
        />
        <form className={styles.nicknameEditBox} onSubmit={onClickEdit}>
          <input
            className={styles.nicknameEdit}
            value={nickname}
            onChange={onHandleNickname}
          ></input>
          {updateImageFile || nickname !== userStates.name ? (
            <button className={styles.editBtn}>수정 완료</button>
          ) : (
            <button className={styles.editBtnNone}>수정 완료</button>
          )}
        </form>
      </section>
    </div>
  );
});

export default ProfileEdit;
