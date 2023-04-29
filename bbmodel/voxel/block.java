public VoxelShape makeShape(){
	VoxelShape shape = VoxelShapes.empty();
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0, 0, 0, 0.0625, 1, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.9375, 0, 0, 1, 1, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0, 0.9375, 1, 0.0625));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.9375, 0.9375, 1, 1));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0.9375, 0.0625, 0.9375, 1, 0.9375));
	shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375, 0.0625, 0.9375));

	return shape;
}