class Issue
  include Mongoid::Document
  include Mongoid::Paperclip

  attr_accessor :image_data
  attr_accessible :description, :image_data, :picture, :user_id, :user
  field :description, type: String

  belongs_to :user

  before_save :decode_base64_image

  has_mongoid_attached_file :picture,
    :styles => {
      :small    => ['100x100#',   :jpg],
      :medium   => ['250x250',    :jpg],
      :large    => ['500x500>',   :jpg]
  }

  def to_hash
    { description: description,
      picture_url: picture.try(:url),
      author: user.name,
      author_apartment: user.apartment
    }
  end

  protected
  def decode_base64_image
    if image_data
      decoded_data = Base64.decode64(image_data)
      content_type = 'jpg'
      original_filename = 'issue.jpg'
      data = StringIO.new(decoded_data)
      data.class_eval do
        attr_accessor :content_type, :original_filename
      end

      data.content_type = content_type
      data.original_filename = File.basename(original_filename)

      self.picture = data
    end
  end

end
