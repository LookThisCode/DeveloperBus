module PasswordHasher
  def hash_password(password, salt)
    Digest::SHA2.hexdigest(password + salt)
  end
end