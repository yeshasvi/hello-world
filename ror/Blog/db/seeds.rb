# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

puts 'DEFAULT USERS'
user2 = User.create! :name => 'yash Singh', :email => 'yash@yash.com', :password => 'changeme', :password_confirmation => 'changeme'
puts 'user: ' << user2.name